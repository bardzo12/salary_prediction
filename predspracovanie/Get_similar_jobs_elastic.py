import psycopg2
import numpy
import elasticsearch
from elasticsearch import helpers
from scipy import sparse
from sklearn.metrics.pairwise import pairwise_distances
import scipy.spatial.distance
import sklearn.preprocessing as pp
from numpy import median
import csv
import sys


#DEFINE SOURCE AND DESTINATION FILES
reload(sys)
sys.setdefaultencoding('utf8')
source_file = 'D:\OneDrive\\4.semester\OZNAL\Train_rev1.csv'
destination_file = 'D:\OneDrive\\4.semester\OZNAL\Train_elastic.csv'

#ELASTICSEARCH CONNECTION
config = {
	    'host': '127.0.0.1'
	}
es = elasticsearch.Elasticsearch([config,],http_auth=('elastic', 'changeme'), timeout=300)

#WRITE COLUMN NAMES TO DESTINATION FILE
with open(destination_file, 'wb') as f:
    filewriter = csv.writer(f, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL,dialect='excel')
    filewriter.writerow(['ID', 'Elastic_min_salary', 'Elastic_max_salary', 'Elastic_avg_salary', 'SalaryNormalized'])

#FIND MOST SIMILAR JOBS AND CALCULATE THEIR MIN,MAX,MEDIAN
i = 1
with open(source_file, 'rb') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
    help = 0
    for row in spamreader:
        if i == 1:
            help = help + 1
            res = es.search(index="salary_prediction", body={
                "_source": {
                    "includes": ["job_title", "job_salary"],
                    "excludes": ["*.job_description"]
                },
                "size": 6,
                "query": {
                    "more_like_this": {
                        "fields": ["job_title^5", "job_description"],
                        "like":
                            {
                                "doc": {
                                    "job_title": row[1],
                                    "job_description": row[2]
                                }
                            }
                    }
                }
            })

            if res['hits']['total'] != 0:
                min = 500000
                max = 0
                count = 0
                sum = []
                for hit in res['hits']['hits']:
                    if row[0] != hit["_id"]:
                        count = count + 1
                        sum.append(int(hit["_source"]["job_salary"]))
                        if int(hit["_source"]["job_salary"]) < min:
                            min = hit["_source"]["job_salary"]
                        if int(hit["_source"]["job_salary"]) > max:
                            max = hit["_source"]["job_salary"]

                # WRITE TO DESTINATION FILE
                with open(destination_file, 'ab') as f:
                    filewriter = csv.writer(f, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL,dialect='excel')
                    if count == 0:
                        filewriter.writerow([row[0], 0, 0, 0, row[10]])
                    else:
                        filewriter.writerow([row[0], min, max, median(sum), row[10]])
                f.close()
            if help%1000 == 0:
                print help