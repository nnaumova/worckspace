package stats;
import rover.Point;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleRoverStatsModule implements RoverStatsModule {
	 
    private ArrayList<Point> visitedPoints;

    public SimpleRoverStatsModule() {
        visitedPoints = new ArrayList<>();

    }
    public void registerPosition(Point point) {
        if (!isVisited(point)) {
            visitedPoints.add(point);
        }
    }

    @Override
    public boolean isVisited(Point point) {
        return visitedPoints.contains(point);
    }

    @Override
    public Collection<Point> getVisitedPoints() {
        return visitedPoints;
    }
}
