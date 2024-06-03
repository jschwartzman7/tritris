import math

def getMultiplicativeInverse(g, p): # p prime, g a primitive root modulo p
    if g == 1:
        return 1
    gPower = 2
    while pow(g, gPower, p) % p != 1:
        gPower += 1
    return pow(g, gPower-1, p)


def babyGiant(p, g, b): # p prime, g a primitive root modulo p, b a member of Z/p
    m = math.ceil(math.sqrt(p))
    babyList = []
    for i in range(m):
        babyList.append(pow(g, i, p))
    invG = getMultiplicativeInverse(g, p)
    for i in range(m):
        val = (b*pow(invG, i*m)) % p
        for j in range(len(babyList)):
            if babyList[j] == val:
                x = i*m + j
                return x % p
    return None

#print("(a): ", babyGiant(71, 11, 21))
#print("(b): ", babyGiant(3571, 2, 3))
print("(c): ", babyGiant(65537, 3, 1729))
