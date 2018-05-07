import csv
import collections
import matplotlib.pyplot as plt
import seaborn as sns
from numpy import median

#DESTINATION AND SOURCE FILES
source_file = 'D:\OneDrive\\4.semester\OZNAL\Train_without_description.csv'
destination_file = 'D:\OneDrive\\4.semester\OZNAL\Train_median_salary_company.csv'


#HELP DICTIONARIES AND ARRAYS
employers = {}
employers_avg = {}
employers_min = {}
employers_max = {}
salaries = []
employerss = []

#READ ALL DATASET AND ADD TO DICTIONARIES
with open(source_file, 'rb') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
    i = 0
    for row in spamreader:
        if i == 0:
            i = 1
            continue
        salaries.append(int(row[9]))
        employerss.append(row[6])
        if row[6] not in employers:
            employers[row[6]] = []
            employers[row[6]].append(row[9])
        else:
            employers[row[6]].append(row[9])

#CALCULATE MIN,MAX,MEDIAN FOR EACH CATEGORY
for emp in employers:
    avg = 0
    min = 1000000000
    max = 0
    help = []
    for i in range(len(employers[emp])):
        help.append(int(employers[emp][i]))
        if int(employers[emp][i]) < min:
            min = int(employers[emp][i])
        if int(employers[emp][i]) > max:
            max = int(employers[emp][i])

        #avg = avg + int(employers[emp][i])
    #avg = avg / float(len(employers[emp]))
    employers_avg[emp] = median(help)
    employers_max[emp] = max
    employers_min[emp] = min

#WRITE TO DESTINATION FILE
with open(destination_file, 'wb') as csvfile:
    filewriter = csv.writer(csvfile, delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
    filewriter.writerow(['Company_median_salary','Company_min_salary','Company_max_salary','SalaryNormalized'])
    for i in range(len(salaries)):
        filewriter.writerow([employers_avg[employerss[i]],employers_min[employerss[i]],employers_max[employerss[i]], salaries[i]])
