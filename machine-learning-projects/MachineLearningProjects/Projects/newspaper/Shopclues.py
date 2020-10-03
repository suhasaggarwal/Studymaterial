require 'nokogiri'
require 'httparty'
require 'zlib'
require 'multi_json'

# Get the main document
document = Nokogiri::XML(HTTParty.get('http://www.flipkart.com/sitemap/sitemap_index.xml').body)

# Extract the archive link list
xml_archives = document.css('sitemap loc').inject([]) do |result, node|
  result.push(node.children[0].content)
end

sitemap = xml_archives.inject({}) do |result, archive_url|

  p "> GET " + archive_url
  response = HTTParty.get(archive_url)

  p "Unzipping"
  string = StringIO.new(response.body.to_s)
  binding.pry
  unzipped = Zlib::GzipReader.new(string).read

  p "Selecting details"
  url_list = []

  # Using a SAX parser instead of CSS selectors because CSS
  # selectors are mind-numbingly slow in comparison.
  archive_document = Nokogiri::XML::Reader(unzipped)
  archive_document.each do |node|
    # node_type == 1 is for the opening tag
    if node.name == 'loc' && node.node_type == 1
      # Read content in between the `loc` tag.
      node.read
      print '#'
      url_list.push(node.value)
    end
  end

  result[archive_url] = url_list
  result
end

File.open('sitemap.dump', 'w') do |file|
  file.write(MultiJson.encode(sitemap))
end