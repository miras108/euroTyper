package org.arpit.java2blog.controller;

import com.euro.typer.data.source.criteria.DateHelper;
import com.euro.typer.data.source.dao.MatchDao;
import com.euro.typer.data.source.entity.Country;
import com.euro.typer.data.source.entity.Match;
import org.arpit.java2blog.score.DayScoreCalculator;
import org.arpit.java2blog.score.DayScoreCreator;
import org.arpit.java2blog.score.OverallScoreCalculator;
import org.arpit.java2blog.score.Score;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ScoreService {

	private final static String SCORE_REGEX = "^[0-9]*:[0-9]*$";

	private DayScoreCreator dayScoreCreator;
	private DayScoreCalculator dayScoreCalculator;
	private OverallScoreCalculator overallScoreCalculator;

	@RequestMapping(value = "/creteDailyScore", method = RequestMethod.GET,headers="Accept=application/json")
	public String getCountries(
			@RequestParam(value="date") String stringDate) throws ParseException {
		Date date = null;
		try {
			date = DateHelper.getDateFromStringWithoutTime(stringDate);
		} catch (ParseException e) {
			return "invalid date, correct date format: dd.MM";
		}

		return dayScoreCreator.createDayScore(date);
	}

	@RequestMapping(value = "/printdayscore", method = RequestMethod.GET,headers="Accept=application/json")
	public List<Score> printDayScore(
			@RequestParam(value="date") String stringDate) throws ParseException {
		Date date = null;
		try {
			date = DateHelper.getDateFromStringWithoutTime(stringDate);
		} catch (ParseException e) {
			Score score = new Score();
			score.setMessage("invalid date, correct date format: dd.MM");
			return Arrays.asList(score);
		}

		return dayScoreCalculator.printDayScore(date);
	}

	@RequestMapping(value = "/printoverallscore", method = RequestMethod.GET,headers="Accept=application/json")
	public List<Score> printOverallScore() throws ParseException {
		return overallScoreCalculator.printOverallScore();
	}


	@Resource
	public void setDayScoreCreator(DayScoreCreator dayScoreCreator) {
		this.dayScoreCreator = dayScoreCreator;
	}

	@Resource
	public void setDayScoreCalculator(DayScoreCalculator dayScoreCalculator) {
		this.dayScoreCalculator = dayScoreCalculator;
	}

	@Resource
	public void setOverallScoreCalculator(OverallScoreCalculator overallScoreCalculator) {
		this.overallScoreCalculator = overallScoreCalculator;
	}
}
