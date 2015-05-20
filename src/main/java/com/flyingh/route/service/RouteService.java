package com.flyingh.route.service;

import com.flyingh.route.vo.Route;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class RouteService {
    private static List<Route> routes;
    private static Map<String, List<Route>> startPointRoutesMap;
    private static Map<String, List<Route>> endPointRoutesMap;

    static {
        try {
            routes = Files.lines(Paths.get(RouteService.class.getClassLoader().getResource("routes.list").toURI()))
                    .map(line -> line.split("", 3)).map(Route::new).collect(toList());
            startPointRoutesMap = routes.stream().collect(groupingBy(Route::getFrom));
            endPointRoutesMap = routes.stream().collect(groupingBy(Route::getTo));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<String> searchWithMaxDistanceAndFormat(String startPoint, String endPoint, int maxDistance) {
        return format(searchWithMaxDistance(startPoint, endPoint, maxDistance), endPoint);
    }

    public static List<List<Route>> searchWithMaxDistance(String startPoint, String endPoint, int maxDistance) {
        List<List<Route>> result = new ArrayList<>();
        if (maxDistance <= 0) {
            return result;
        }
        List<Route> routes = startPointRoutesMap.get(startPoint);
        if (isEmpty(routes)) {
            return result;
        }
        routes.stream().map(route -> {
            List<List<Route>> tmp = new ArrayList<>();
            if (route.endPoints().equals(startPoint + endPoint) && route.getDistance() < maxDistance) {
                tmp.add(new ArrayList<>(Arrays.asList(route)));
            }
            List<List<Route>> tmp2 = searchWithMaxDistance(route.getTo(), endPoint, maxDistance - route.getDistance());
            if (!isEmpty(tmp2)) {
                tmp2.forEach(list -> list.add(0, route));
            }
            tmp.addAll(tmp2);
            return tmp;
        }).reduce(result, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
        return result;
    }

    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static int shortestDistance(String startPoint, String endPoint) {
        List<List<Route>> lists = searchDirectly(startPoint, endPoint, null);
        return lists.stream().mapToInt(list -> list.stream().collect(summingInt(Route::getDistance))).min().getAsInt();
    }

    public static List<List<Route>> searchDirectly(String startPoint, String endPoint, String previousStartPoint) {
        List<List<Route>> result = new ArrayList<>();
        Optional<Route> optional = routes.stream().filter(route -> route.endPoints().equals(startPoint + endPoint)).findFirst();
        if (optional.isPresent()) {
            result.add(new ArrayList<>(Arrays.asList(optional.get())));
            return result;
        }
        List<Route> routes = startPointRoutesMap.get(startPoint);
        if (isEmpty(routes)) {
            return result;
        }
        routes.stream().filter(route -> !route.getTo().equals(previousStartPoint)).map(route -> {
            List<List<Route>> tmpResult = searchDirectly(route.getTo(), endPoint, startPoint);
            tmpResult.forEach(list -> list.add(0, route));
            return tmpResult;
        }).reduce(result, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
        return result;
    }

    public static List<String> searchWithMaxStopAndFormat(String startPoint, String endPoint, int maxStop) {
        return format(searchWithMaxStop(startPoint, endPoint, maxStop), endPoint);
    }

    private static List<String> format(List<List<Route>> lists, String endPoint) {
        return lists.stream()
                .map(l -> l.stream().map(Route::getFrom).collect(collectingAndThen(joining("-"), str -> str + "-" + endPoint)))
                .collect(toList());
    }

    public static List<List<Route>> searchWithMaxStop(String startPoint, String endPoint, int maxStop) {
        return IntStream.rangeClosed(1, maxStop).boxed().flatMap(stop -> searchWithExactStop(startPoint, endPoint, stop).stream()).collect(toList());
    }

    public static List<String> searchWithExactStopAndFormat(String startPoint, String endPoint, int exactStop) {
        return format(searchWithExactStop(startPoint, endPoint, exactStop), endPoint);
    }

    public static List<List<Route>> searchWithExactStop(String startPoint, String endPoint, int exactStop) {
        List<List<Route>> result = new ArrayList<>();
        if (exactStop == 1) {
            Optional<Route> routeOptional = routes.stream().filter(route -> route.endPoints().equals(startPoint + endPoint)).findFirst();
            if (routeOptional.isPresent()) {
                result.add(new ArrayList<>(Arrays.asList(routeOptional.get())));
            }
            return result;
        }
        List<Route> routes = endPointRoutesMap.get(endPoint);
        if (isEmpty(routes)) {
            return result;
        }
        routes.stream().map(route -> {
            List<List<Route>> tmpResult = new ArrayList<>();
            List<List<Route>> partResult = searchWithExactStop(startPoint, route.getFrom(), exactStop - 1);
            partResult.forEach(list -> list.add(route));
            tmpResult.addAll(partResult);
            return tmpResult;
        }).reduce(result, (list1, list2) -> {
            if (!isEmpty(list2)) {
                list1.addAll(list2);
            }
            return list1;
        });
        return result;
    }

    public static int getDistance(String path) throws Exception {
        List<String> routes = getEndPoints(path);
        if (!isRouteExists(routes)) {
            throw new Exception(path + ":NO SUCH ROUTE");
        }
        return RouteService.routes.stream().filter(route -> routes.contains(route.endPoints())).collect(summingInt(Route::getDistance));
    }

    public static List<String> getEndPoints(String path) {
        if (path == null || path.length() < 3 || !path.contains("-")) {
            throw new IllegalArgumentException();
        }
        ArrayList<String> result = new ArrayList<>();
        path = path.replaceAll("-", "");
        for (int i = 0; i < path.length() - 1; i++) {
            result.add(path.substring(i, i + 2));
        }
        return result;
    }

    private static boolean isRouteExists(List<String> routes) {
        return RouteService.routes.stream().map(Route::endPoints).collect(toList()).containsAll(routes);
    }

    public static List<Route> getRoutes() {
        return routes;
    }

    public static Map<String, List<Route>> getStartPointRoutesMap() {
        return startPointRoutesMap;
    }

    public static Map<String, List<Route>> getEndPointRoutesMap() {
        return endPointRoutesMap;
    }
}
