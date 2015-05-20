package com.flyingh.route.vo;

public class Route {
    private String from;
    private String to;
    private int distance;

    public Route(String from, String to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(String[] params) {
        this(params[0], params[1], Integer.parseInt(params[2]));
    }

    public int getDistance() {
        return distance;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (distance != route.distance) return false;
        if (from != null ? !from.equals(route.from) : route.from != null) return false;
        return !(to != null ? !to.equals(route.to) : route.to != null);

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + distance;
        return result;
    }

    @Override
    public String toString() {
        return "" + from + to + distance;
    }

    public String endPoints() {
        return "" + from + to;
    }
}
