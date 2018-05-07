import psycopg2
import numpy
import elasticsearch
from elasticsearch import helpers
from scipy import sparse
from sklearn.metrics.pairwise import pairwise_distances
import scipy.spatial.distance
import sklearn.preprocessing as pp
import csv
import sys

reload(sys)
sys.setdefaultencoding('utf8')

#DATASET SOURCE FILE
source_file = 'D:\OneDrive\\4.semester\OZNAL\Train_rev1.csv'

#CONNECT TO ELASTIC
config = {
	    'host': '127.0.0.1'
	}
es = elasticsearch.Elasticsearch([config,],http_auth=('elastic', 'changeme'), timeout=300)


#INDEX ALL ITEMS TO ELASTIC
i = 0
j = 0
actions = []
with open(source_file, 'rb') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
    for row in spamreader:
        if i == 0:
            i = 1
            continue
        j = j+1
        action = {
                    "_index": "salary_prediction",
                    "_type": "jobs",
                    "_id": row[0],
                    "job_title": row[1],
                    "job_description": row[2],
                    "job_salary": int(row[10])
                }
        actions.append(action)
        if j == 500:
            j=0
            helpers.bulk(es,actions)
            actions = []
    helpers.bulk(es, actions)