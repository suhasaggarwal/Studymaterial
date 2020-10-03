from gensim.models import Doc2Vec
from gensim.models import word2vec_model
import numpy as np

def avg_feature_vector(words, model, num_features, index2word_set):
    # function to average all words vectors in a given paragraph
    featureVec = np.zeros((num_features,), dtype="float32")
    nwords = 0

    # list containing names of words in the vocabulary
    # index2word_set = set(model.index2word) this is moved as input param for performance reasons
    for word in words:
        if word in index2word_set:
            nwords = nwords + 1
            featureVec = np.add(featureVec, model[word])

    if (nwords > 0):
        featureVec = np.divide(featureVec, nwords)
    return featureVec


sentence_1 = "this is sentence number one"
sentence_1_avg_vector = avg_feature_vector(sentence_1.split(), model=word2vec_model, num_features=300)

#get average vector for sentence 2
sentence_2 = "this is sentence number two"
sentence_2_avg_vector = avg_feature_vector(sentence_2.split(), model=word2vec_model, num_features=300)

sen1_sen2_similarity =  1 - spatial.distance.cosine(sentence_1_avg_vector,sentence_2_avg_vector)