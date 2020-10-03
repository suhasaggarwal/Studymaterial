# Create your first MLP in Keras
from keras.models import Sequential
from keras.layers import Dense
import numpy
from pandas import read_csv
from sklearn.preprocessing import LabelEncoder
from keras.utils import np_utils
from keras.layers import Dropout
import numpy
from pandas import read_csv
from keras.models import Sequential
from keras.layers import Dense
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.model_selection import cross_val_score
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import StratifiedKFold
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline
# fix random seed for reproducibility
seed = 7
numpy.random.seed(seed)
# load pima indians dataset
data = read_csv('train.csv')
class_counts = data.groupby('target').size()
print(class_counts)
dataset = data.head(2000)
# split data into X and y
#print(dataset)
#X = dataset.iloc[:,0:94].astype(float)
#Y = dataset.iloc[:,94]
data=data.values
X=data[:,0:94]
Y=data[:,94]
# encode string class values as integers
encoder = LabelEncoder()
encoder.fit(Y)
encoded_Y = encoder.transform(Y)
# convert integers to dummy variables (i.e. one hot encoded)
dummy_y = np_utils.to_categorical(encoded_Y)
# create model
#print(Y.nunique)
#model = Sequential()
#model.add(Dense(94, input_dim=94, activation='relu'))
#model.add(Dropout(0.5))
#model.add(Dense(47, activation='relu'))
#model.add(Dropout(0.5))
#model.add(Dense(9, activation='softmax'))

# Compile model
#model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
# Fit the model
#model.fit(X,dummy_y, epochs=10, batch_size=150)
# evaluate the model
#scores = model.evaluate(X, dummy_y)
#print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))


def create_larger():
	# create model
	model = Sequential()
	model.add(Dense(94, input_dim=94, kernel_initializer='normal', activation='relu'))
	#model.add(Dropout(0.2))
	#model.add(Dense(47, kernel_initializer='normal', activation='relu'))
	#model.add(Dropout(0.2))
	model.add(Dense(9, kernel_initializer='normal', activation='softmax'))
	# Compile model
	model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
	return model
estimators = []
estimators.append(('standardize', StandardScaler()))
estimators.append(('mlp', KerasClassifier(build_fn=create_larger, epochs=100, batch_size=5, verbose=3)))
pipeline = Pipeline(estimators)
kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=seed)
results = cross_val_score(pipeline, X, encoded_Y, cv=kfold,verbose=3)
print("Larger: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))
