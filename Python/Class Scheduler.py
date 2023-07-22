#Class Scheduler
import itertools
#import selenium
'''("L24 Math 318", (1, 3, 5), (1300, 1350), 2),
    ("E81 CSE 332S", (2, 4), (1130, 1250), 1),
    ("L11 Econ 413", (2, 4), (1000, 1120), 4),
    ("L24 Math 407", (2, 4), (1300, 1420), 3)
    ]'''

possibleClasses = [
    ("Calculus of Several Variables", {1: (1300, 1350), 3: (1300, 1350), 5: (1300, 1350)}, 2),
    ("Object-Oriented Software Development Laboratory", {2: (1130, 1250), 4: (1130, 1250)}, 1),
    ("Introduction to Econometrics", {2: (1000, 1120), 4: (1000, 1120)}, 4),
    ("An Introduction to Differential Geometry", {2: (1300, 1420), 4: (1300, 1420)}, 3),
    ("Introduction to Machine Learning", {1: (1430, 1550), 3: (1430, 1550)}, 3),
    ("Linear Algebra", {1: (1100, 1150), 3: (1100, 1150), 5: (1100, 1150)}, 3)
    ]
    #   (name, day:times, rank)

def makeSchedules(possibleClasses, numClasses, earliestStart=0000, latestEnd=2400):
    validSchedules = []
    visitedCombinations = []
    for schedule in itertools.product(possibleClasses, repeat = numClasses):
        #prune certain schedules
        classSet = {course[0] for course in schedule}
        if classSet not in visitedCombinations:
            if isValid(schedule, earliestStart, latestEnd):
                validSchedules.append(schedule)
        visitedCombinations.append(classSet)

    if len(validSchedules) > 0:
        outputSchedules(validSchedules)
    else:
        print("No valid schedules")

def isValid(schedule, earliestStart, latestEnd):
    
    # Constraints:
    #   Names cannot be the same for any 2 classes
    #   Time and day cannot overlap for any 2 classes
    #   Must start after earliestStart
    #   Must end before lasestEnd
    courseTimes = []
    courseNames = []
    for curCourse in schedule:
        # Check if two courses have the same namne
        if curCourse[0] in courseNames:#['Calculus of Several Variables', 'Object-Oriented Software Development Laboratory', 'Introduction to Econometrics', 'An Introduction to Differential Geometry', 'Introduction to Machine Learning', 'Linear Algebra']:
            return False

        courseNames.append(curCourse[0])

        # Check if class starts before earliest time or ends after latest time
        for time in curCourse[1].values():
            if time[0] < earliestStart or time[1] > latestEnd:
                return False
   
        # Check if any two courses overlap in time
        for day in curCourse[1]:
            for dayAndTime in courseTimes:
                if day == dayAndTime[0]:
                    if curCourse[1][day][0] > dayAndTime[1][0] and curCourse[1][day][0] < dayAndTime[1][1]: # course starts after this starts and course starts before this ends
                        return False
                    else: # course starts before this course starts
                        if curCourse[1][day][1] > dayAndTime[1][0]: # course ends after this starts
                            return False

        for dayAndTime in curCourse[1].items():
            courseTimes.append(dayAndTime)
       
    return True
        
def outputSchedules(validSchedules):
    daysMap = {
        1: 'Monday',
        2: 'Tuesday',
        3: 'Wednesday',
        4: 'Thursday',
        5: 'Friday'
        }
    print("Schedules found: ", len(validSchedules))
    print("")
    for i in range(len(validSchedules)):
        print("Schedule ", i + 1, ":")
        print("")
        for j, course in enumerate(validSchedules[i]):
            print("Course ", j+1, ": ", course[0])
            for item in course[1].items():
                print(daysMap[item[0]], toTime(item[1][0]), "to ", toTime(item[1][1]))
            print("")

def toTime(time): # 1420 to 2:20
    num = time % 1200
    num = str(num)
    num = num[:-2] + ":" + num[-2:]
    return num



#makeSchedules(possibleClasses, 4)
print(6)
'''for schedule in makeSchedules(possibleClasses, 3):
    for course in schedule:
        print(course[0])
    print(schedule)
    print(" ")
    print(" ")'''

'''numbers = ['1', '2', '3', '3', '3']
nums = []
for number in numbers:
    if number in nums:
        print("NOTNOT")
    nums.append(number)


ff = []
for item in itertools.product(['a', 'b', 'c'], repeat = 3):
    used = []
    good = True
    for letter in item:
        if letter in used:
            good = False
        used.append(letter)
    if good:
        ff.append(item)
for item in ff:
    print(item)'''

#llst = []
'''dict = {1:'a', 2:'b', 3:'c'}
for item in dict:
    print(item)'''