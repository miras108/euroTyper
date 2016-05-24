package com.euro.typer.score;


import org.junit.Test;

/**
 * Created by miras108 on 2016-05-21.
 */
public class ScoreComparatorTest {

    @Test
    public void test()
    {
        String score = "1:2";

        ScoreResultCalculator scoreComparator = new ScoreResultCalculator();
        scoreComparator.calculateScore(score, "1:2");

    }
}