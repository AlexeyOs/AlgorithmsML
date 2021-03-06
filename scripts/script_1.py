#! /usr/bin/env python
# -*- coding: utf-8 -*-
# python 3

# [1] Считывание файла
import inline as inline
import matplotlib
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split


def get_data(train_file, test_file = None):
    if test_file == None:
        frame = pd.read_csv(train_file)
        data = frame.values
        np.random.shuffle(data)
        return data
    else:
        train_frame = pd.read_csv(train_file)
        test_frame = pd.read_csv(test_file)

        train_data = train_frame.values
        test_data = test_frame.values
        np.random.shuffle(train_data)
        np.random.shuffle(test_data)

        return train_data, test_data


def get_training_testing_sets(train_file, test_file = None):
    if test_file == None:
        data = get_data(train_file)
        train_data, test_data = train_test_split(data)
    else:

        train_data, test_data = get_data(train_file, test_file)

    X_train = train_data[:, 1:]
    Y_train = train_data[:, :1]
    X_test = test_data[:, 1:]
    Y_test = test_data[:, :1]

    print(X_train.shape, X_test.shape)
    
    return X_train, Y_train, X_test, Y_test




data = get_data('../input/SPAM text message 20170820 - Data.csv')
m = data.shape[0]

print(data[0])


# [2] Фильтрация данных
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.preprocessing import LabelEncoder
import string
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
import nltk

nltk.download('wordnet')


punctuations = string.punctuation
stopwords = stopwords.words('english')
lemmatizer = WordNetLemmatizer()

for i in range(m):
    data[i][1] = ''.join(j for j in data[i][1] if j not in punctuations)
    data[i][1] = ' '.join(lemmatizer.lemmatize(j.lower()) for j in data[i][1].split() if j not in stopwords)

print(data[0])


# [3] По категоряим
filtered_spam_data = ''
filtered_ham_data = ''
for i in range(data.shape[0]):
    filtered_spam_data += ' '.join(j for j in data[i][1].split() if data[i][0] == 'spam')
    filtered_ham_data += ' '.join(j for j in data[i][1].split() if data[i][0] == 'ham')

from wordcloud import WordCloud
from matplotlib import pyplot as plt
#%matplotlib inline

import jupyterlab
import notebook

#from IPython import get_ipython
#get_ipython().run_line_magic('matplotlib', 'inline')

x, y = np.ogrid[:300, :300]
mask = (x - 150) ** 2 + (y - 150) ** 2 > 130 ** 2
mask = 255 * mask.astype(int)

wc = WordCloud(max_font_size=40, max_words=200, background_color='white', random_state=1337, mask=mask).generate(
    filtered_spam_data)
plt.figure(figsize=(10, 10))
plt.imshow(wc, interpolation='bilinear')
plt.axis("off")
plt.title("Spam Words", fontsize=20)
plt.show()

wc = WordCloud(max_font_size=40, max_words=200, background_color='white', random_state=1337, mask=mask).generate(
    filtered_ham_data)
plt.figure(figsize=(10, 10))
plt.imshow(wc, interpolation='bilinear')
plt.axis("off")
plt.title("Ham Words", fontsize=20)
plt.show()


# [4] Результат в колличестве

from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer
from sklearn.preprocessing import LabelEncoder



# cv = TfidfVectorizer(ngram_range=(1, 2))
print(data[:, 1])
cv = TfidfVectorizer()
# Задаются веса для выборки из категории спам
X = cv.fit_transform(data[:, 1])
print(X[0])

le = LabelEncoder()
print(data[:, 0])
# Категории spam и ham преобразовываются в 0 и 1
Y = le.fit_transform(data[:, 0])
print(Y[0])


X_test = X[:1500, :]
X_train = X[1500:, :]

Y_test = Y[:1500]
Y_train = Y[1500:]

print('X_train', X_train.shape)
print('X_test', X_test.shape)
print('Y_train', Y_train.shape)
print('Y_test', Y_test.shape)

# [5] Результат в процентах

from sklearn.naive_bayes import BernoulliNB
from sklearn.svm import LinearSVC
from sklearn.tree import DecisionTreeClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, confusion_matrix
import warnings

with warnings.catch_warnings():
    warnings.simplefilter("ignore")
    results = []
    for clf, name in [
                      (BernoulliNB(alpha=0.4), 'Native Bayes'),
                      (LinearSVC(C=9), 'SVC'),
                      (DecisionTreeClassifier(max_depth=26), 'DecisionTreeClassifier',),
                      # (LogisticRegression(C=12), 'LogisticRegression'),
                      # (RandomForestClassifier(max_depth=2, random_state=0), 'RandomForest'),
                      (KNeighborsClassifier(n_neighbors=13), 'KNN')
    ]:
        #     Y_train.reshape(Y_train.shape[0],)
        #     Y_test.reshape(Y_test.shape[0])
        clf.fit(X_train, Y_train)

        predictions = clf.predict(X_train)
        training_accuracy = accuracy_score(predictions, Y_train)

        m_test = X_test.shape[0]
        predictions = clf.predict(X_test)
        accuracy = accuracy_score(predictions, Y_test)
        confusion = confusion_matrix(predictions, Y_test)

        results.append([name, training_accuracy, accuracy, confusion])

for result in results:
    print(result[0], result[1], result[2])
    print(result[3])