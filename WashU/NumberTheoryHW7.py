import numpy as np
from matplotlib import pyplot as plt
import math


def isPrime(x): # O(x/logx)
    for i in range(2, (int)(x**(0.5)+1)):
        if x % i == 0:
            return False
    return True

def PollardP(n, maxIterations):
    factorBound = int(n**(1/6))
    iteration = 0
    while iteration < maxIterations:
        iteration += 1
        print("i: ", iteration)
        M = 1
        for i in range(2, factorBound+1):
            if isPrime(i):
                M*=i**(int(math.log(factorBound, i)))
        testGCD = math.gcd((2**M-1), n) # asssuming n is odd
        if testGCD == 1:
            factorBound += 1
        elif testGCD == n:
            factorBound -= 1
        else:
            return testGCD
    print("No factors found for ", n)
    return None



'''Write code that counts the number of Miller-Rabin witnesses for an arbitrary odd composite
integer N , and use it to find the smallest (odd composite) N other than N = 9 for which the number
of witnesses is less than 4/5(N - 1). How many witnesses does this N have?'''

def countMRWitnesses(n):
    




#print(PollardP(89974, 100))


