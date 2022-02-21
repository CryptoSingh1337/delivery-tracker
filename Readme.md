# Delivery Tracker
A delivery tracker system which will suggest a rider or an order according to the distance between them. Entity with the minimum distance is preferred.

It calculate the distance between two points using the Euclidean Distance = `sqrt((x2-x1)^2 + (y2-y1)^2)`
It plots all the longitude and latitude coordinates on the 2D plane.

### Controllers
- **RiderController:** This will handle all the request related to the rider.
- **OrderController:** This will handle all the request related to the order.
- **SuggestionController:** This will handle all the request for getting the nearby order or rider.

### Documentation
[https://documenter.getpostman.com/view/13580425/UVkmQby6](https://documenter.getpostman.com/view/13580425/UVkmQby6)

### Sample 2D plane
https://www.desmos.com/calculator/mgwkmpmrwc