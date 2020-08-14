import noise
import numpy as np
from PIL import Image
import math
import pygame as pg

class Terrain:
    
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.terrain = np.zeros((self.height, self.width))
        self.topColorTerrain = np.zeros((height, width, 3), dtype=np.uint8)
        self.frontTerrain = None
        self.frontColorTerrain = None
        
        self.WATER = [0,191,255]
        self.SAND = [238, 214, 175]
        self.GRASS = [34,139,34]
        self.TREES = [0, 100, 0]
        self.ROCK = [139, 137, 137]
        self.SNOW = [255, 250, 250]
        self.SKY =  [135, 206, 235]

    def generateTopTerrain(self, scale, octaves, persistence, lacunarity, seed):
        for x in range(0, self.height):
            for y in range(0, self.width):
                self.terrain[x][y] = noise.pnoise2(x/scale, y/scale, octaves, persistence, lacunarity, self.height, self.width, seed)
    
    def generateTopColor(self, BLUE, YELLOW, GREEN, DARKGREEN, GRAY, WHITE):
        for x in range(0, self.height):
            for y in range(0, self.width):
                if(self.terrain[x, y] < BLUE):
                    self.topColorTerrain[x, y] = self.WATER
                elif(self.terrain[x, y] < YELLOW):
                    self.topColorTerrain[x, y] = self.SAND
                elif(self.terrain[x, y] < GREEN):
                    self.topColorTerrain[(x, y)] = self.GRASS
                elif(self.terrain[x, y] < DARKGREEN):
                    self.topColorTerrain[(x, y)] = self.TREES
                elif(self.terrain[x, y] < GRAY):
                    self.topColorTerrain[x, y] = self.ROCK
                else:
                    self.topColorTerrain[x, y] = self.SNOW
    
    def makeIsland(self, size):
        centerX = self.width/2
        centerY = self.height/2
        circleTerrain = np.zeros_like(self.terrain)
        
        for y in range(0, len(self.terrain)):
            for x in range(0, len(self.terrain[0])):
                numX = abs(x - centerX)
                numY = abs(y - centerY)
                totalDistance = math.sqrt(numX*numX + numY*numY)
                circleTerrain[y][x] = totalDistance
                
        maxTerrain = np.max(circleTerrain)
        circleTerrain /= maxTerrain
        circleTerrain -= 0.5
        circleTerrain *= 2.0
        circleTerrain = -circleTerrain
        
        for y in range(0, len(self.terrain)):
            for x in range(0, len(self.terrain[0])):
                if circleTerrain[y][x] > 0:
                    circleTerrain[y][x] *= 20
                    
        maxTerrain = np.max(circleTerrain)
        circleTerrain = circleTerrain / maxTerrain
        
    def generateFrontTerrain(self, benchmark = 0.50):
        self.frontTerrain = np.zeros((100, self.width, 2), dtype=np.uint8)
        for y in range(0, len(self.frontTerrain)): #Loop through all of the front cords starting at top left
            for x in range(0, len(self.frontTerrain[0])):
                for z in range(0, self.height):
                    if(self.terrain[z][x] > benchmark):
                        self.frontTerrain[y][x][0] = z
                        self.frontTerrain[y][x][1] = x
            benchmark -= 0.0075
            print(y/len(self.frontTerrain))
            
    def generateFrontColor(self):
        self.frontColorTerrain = np.zeros((100, self.width, 3), dtype=np.uint8)
        for y in range(0, len(self.frontTerrain)):
            for x in range(0, len(self.frontTerrain[0])):
                if self.frontTerrain[y][x][0] != 0 and self.frontTerrain[y][x][1] != 0:
                    self.frontColorTerrain[y][x] = self.topColorTerrain[self.frontTerrain[y][x][0]][self.frontTerrain[y][x][1]]
                    
                else:
                    self.frontColorTerrain[y][x] = self.SKY
                    
    def findMaxHeight(self):
        max = self.terrain[0][0]
        for y in range(0, self.height):
            for x in range(0, self.width):
                if self.terrain[y][x] > max:
                    max = self.terrain[y][x]
        return max
    
    def findMinHeight(self):
        min = self.terrain[0][0]
        for y in range(0, self.height):
            for x in range(0, self.width):
                if self.terrain[y][x] < min:
                    min = self.terrain[y][x]
        return min
                
    def drawTerrain(self):
        return Image.fromarray(self.topColorTerrain)
    
    def raiseTerrain(self, ammount):
        for y in range(0, len(self.terrain)):
            for x in range(0, len(self.terrain[0])):
                self.terrain[y][x] += ammount
    
    def printTerrain(self, screen):
        for y in range(0, len(self.terrain)):
            for x in range(0, len(self.terrain[0])):
                pg.draw.circle(screen, self.topColorTerrain[(x, y)], [x, y], 0)
    
    def drawSideImage(self):
        return Image.fromarray(self.frontColorTerrain)