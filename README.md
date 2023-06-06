# dijikstra


Dijkstra's algorithm is a popular algorithm for finding the shortest path between two nodes in a weighted graph. 
The algorithm works by maintaining a set of nodes for which the shortest path has already been found, 
and a set of nodes for which the shortest path is yet to be determined. At each iteration, the algorithm selects the node with the
smallest tentative distance from the set of nodes for which the shortest path is yet to be determined, and updates the tentative distances of its neighbors.

Here are the steps of Dijkstra's algorithm:

Initialize all node distances to infinity and mark the starting node with distance 0.
Create an empty set of visited nodes.
While the set of visited nodes does not contain the destination node:
a. Select the node with the smallest tentative distance from the set of unvisited nodes.
b. For each of its neighbors that is not already visited, calculate the tentative distance from the starting node to that neighbor.
c. If the tentative distance is less than the current distance, update the distance.
d. Mark the selected node as visited.
Return the shortest distance to the destination node.
The algorithm can be implemented using a priority queue to efficiently select the node with the smallest tentative distance at each iteration. Dijkstra's algorithm guarantees that the shortest path is found when all edge weights are non-negative.


https://user-images.githubusercontent.com/98056148/232041034-ba5657d7-5b44-4611-8e8e-94c7578fa891.mp4

![Screenshot (156)](https://user-images.githubusercontent.com/98056148/232040192-66ada86e-d227-424d-9605-3969618c1400.png)
![Screenshot (155)](https://user-images.githubusercontent.com/98056148/232040199-42033913-6d25-4c42-9707-ebb3ac212f29.png)
![Screenshot (154)](https://user-images.githubusercontent.com/98056148/232040206-8e287cb1-86e6-4edb-807e-75fe5864500c.png)
![Screenshot (153)](https://user-images.githubusercontent.com/98056148/232040209-d1cf3d26-1bfd-4dce-9e1b-cf0ff77e47b8.png)
![Screenshot (152)](https://user-images.githubusercontent.com/98056148/232040210-8bfa31da-ed2f-41e9-b255-4198ad6e718e.png)
![Screenshot (151)](https://user-images.githubusercontent.com/98056148/232040211-1ff6d3a9-5b25-4ca3-be71-2d3c9981fe2a.png)




