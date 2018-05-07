import csv
import collections
import matplotlib.pyplot as plt
import seaborn as sns

#SOURCE AND DESTINATION FILES
source_file = 'D:\OneDrive\\4.semester\OZNAL\Train_rev1.csv'
destination_file = 'D:\OneDrive\\4.semester\OZNAL\Train_education_added.csv'

categories = {}
platy = collections.OrderedDict()
total_uknown = 0
part_time_count = 0
full_time_count = 0

#KEY WORDS FOR EACH CATEGORY
PHD = ["phd","ph.d","doctor","Senior"]
Graduate = ["graduate","bachelor","junior"]
Master = ["master","ing.","mgr.","meng."]

i = 0
phd = 0
graduate = 0
master = 0

phds = []
masters = []
graduates = []
salary = []


#CHECK FOR KEYWORDS
with open(source_file, 'rb') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
    for row in spamreader:
        if i == 0:
            i = 1
            continue
        if any(word in row[2].lower() for word in PHD):
            phd = phd + 1
            phds.append(1)
        else:
            phds.append(0)
        if any(word in row[2].lower() for word in Graduate):
            graduate = graduate + 1
            graduates.append(1)
        else:
            graduates.append(0)
        if any(word in row[2].lower() for word in Master):
            master = master + 1
            masters.append(1)
        else:
            masters.append(0)
        salary.append(row[10])

print len(masters)
print len(salary)

#WRITE TO DESTINATION FILE
with open(destination_file, 'wb') as csvfile:
    filewriter = csv.writer(csvfile, delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
    filewriter.writerow(['PhD','Ing/Mgr','Absolvent','SalaryNormalized'])
    for i in range(len(salary)):
        filewriter.writerow([phds[i],masters[i],graduates[i], salary[i]])

print phd
print graduate
print master
