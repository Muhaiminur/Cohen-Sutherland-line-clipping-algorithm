# Cohen-Sutherland-line-clipping-algorithm
Welcome to the Cohen-Sutherland-line-clipping-algorithm wiki!
# Cohen Sutherland line clipping algorithm
![Cohen Sutherland line clipping algorithm](https://github.com/Muhaiminur/Cohen-Sutherland-line-clipping-algorithm/blob/master/Sutherland%20line%20clipping%20algorithm%20example.PNG)

1. Draw 
	a. 1000 random lines
2. Using Cohen Sutherland line clipping algorithm, only draw the lines that are partially accepted. 
3. If any fragment of the line is inside the clipping window give it two different colors
	a. fragment inside the clipping window: Green
	b. otherwise: blue
Hint: initially the partially accepted lines should be drawn using blue. When the line fragment inside the clip window is determined draw the line fragment using green. 
Do not draw trivially accepted or rejected lines. 
