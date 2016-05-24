package org.arpit.java2blog.score;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by miras108 on 2016-05-21.
 */
public class ScoreResultCalculator {

    private static final String REGEX = "([0-9]*)(:)([0-9]*)";

    public static int calculateScore(String typedScore, String realScore)
    {
        return getPointsForResult(typedScore, realScore) + getPointsForExactScore(typedScore, realScore);
    }

    private static int getPointsForResult(String typedScore, String realScore)
    {
        int result1 = compareScore(typedScore);
        int result2 = compareScore(realScore);

        if(result1 == result2){
            return 1;
        }
        return 0;
    }

    private static int compareScore(String score)
    {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(score);

        matcher.find();
        Integer goalForFirstTeam = Integer.parseInt(matcher.group(1));

        Integer goalForSecondTeam = Integer.parseInt(matcher.group(3));
        return goalForFirstTeam.compareTo(goalForSecondTeam);
    }

    private static int getPointsForExactScore(String typedScore, String realScore)
    {
        if(typedScore.equals(realScore))
        {
            return 3;
        }
        return 0;
    }
}
