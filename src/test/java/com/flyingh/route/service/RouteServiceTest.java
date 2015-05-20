package com.flyingh.route.service;

import com.flyingh.route.vo.Route;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RouteServiceTest {

    @Test
    public void test() {
        final String input = "Are you  handsome, OK?!";
        Matcher matcher = Pattern.compile(/*"\\p{L}+"*/"[a-zA-Z]+").matcher(input);
        LinkedList<String> words = new LinkedList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }
        System.out.println(words);

        char[] chars = new StringBuilder(input).reverse().toString().toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean isPreviousLetter = false;
        for (char c : chars) {
            if (Character.isLetter(c)) {
                if (!isPreviousLetter) {
                    isPreviousLetter = true;
                    builder.append(words.removeLast());
                }
            } else {
                isPreviousLetter = false;
                builder.append(c);
            }
        }
        System.out.println(input);
        System.out.println(builder);
    }


    @Test
    public void test1_5() {
        Arrays.asList("A-B-C", "A-D", "A-D-C", "A-E-B-C-D", "A-E-D").forEach(route -> {
            try {
                System.out.println(route + ":" + RouteService.getDistance(route));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Test
    public void test6_7() {
        RouteService.searchWithMaxStopAndFormat("C", "C", 3).forEach(System.out::println);
        System.out.println("*******************************");
        RouteService.searchWithExactStopAndFormat("A", "C", 4).forEach(System.out::println);
    }

    @Test
    public void test8_9() {
        System.out.println(RouteService.shortestDistance("A", "C"));
        System.out.println(RouteService.shortestDistance("B", "B"));
    }


    @Test
    public void test10() {
        RouteService.searchWithMaxDistanceAndFormat("C", "C", 30).stream()
                .map(str -> str.replaceAll("-", "")).collect(Collectors.toSet()).forEach(System.out::println);
    }

    @Test
    public void testSearchWithMaxDistance() {
        RouteService.searchWithMaxDistance("C", "C", 30).stream().collect(Collectors.toSet()).forEach(System.out::println);
    }

    @Test
    public void testSearchWithMaxStopAndFormat() {
        RouteService.searchWithMaxStopAndFormat("C", "C", 2).forEach(System.out::println);
        System.out.println("*********************");
        RouteService.searchWithMaxStopAndFormat("C", "C", 3).forEach(System.out::println);
        System.out.println("*******************************");
        RouteService.searchWithMaxStopAndFormat("B", "B", 5).forEach(System.out::println);
    }

    @Test
    public void testSearchWithMaxStop() {
        RouteService.searchWithMaxStop("C", "C", 2).forEach(System.out::println);
        System.out.println("*********************");
        RouteService.searchWithMaxStop("C", "C", 3).forEach(System.out::println);
    }

    @Test
    public void testSearchWithExactStopAndFormat() {
        RouteService.searchWithExactStopAndFormat("C", "C", 2).forEach(System.out::println);
        System.out.println("*********************");
        RouteService.searchWithExactStopAndFormat("C", "C", 3).forEach(System.out::println);
    }

    @Test
    public void testSearchWithExactStop() {
        RouteService.searchWithExactStop("C", "C", 2).forEach(System.out::println);
        System.out.println("*********************");
        RouteService.searchWithExactStop("C", "C", 3).forEach(System.out::println);
    }

    @Test
    public void testGetEndPoints() {
        RouteService.getEndPoints("A-B-C-D-E-F").forEach(System.out::println);
    }

    @Test
    public void testInit() {
        RouteService.getRoutes().forEach(System.out::println);
        System.out.println("*********************");
        BiConsumer<String, List<Route>> biConsumer = (k, v) -> System.out.println(k + "->" + v);
        RouteService.getStartPointRoutesMap().forEach(biConsumer);
        System.out.println("*********************");
        RouteService.getEndPointRoutesMap().forEach(biConsumer);
    }
}