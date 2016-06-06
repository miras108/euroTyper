package org.arpit.java2blog.controller;

import com.euro.typer.data.source.criteria.DateHelper;
import com.euro.typer.data.source.dao.MatchDao;
import com.euro.typer.data.source.entity.Country;
import com.euro.typer.data.source.entity.Match;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MatchServiceController {

	private final static String SCORE_REGEX = "^[0-9]*:[0-9]*$";

	private MatchDao matchDao;

	@RequestMapping(value = "/addmatch", method = RequestMethod.GET,headers="Accept=application/json")
	public String getCountries(
			@RequestParam(value="date") String date,
			@RequestParam(value = "country1") String stringCountry1,
			@RequestParam(value = "country2") String stringCountry2) throws ParseException {

		Country country1 = Country.getFromString(stringCountry1);
		if(country1 == null)
		{
			return "invalid country1";
		}

		Country country2 = Country.getFromString(stringCountry2);
		if(country2 == null)
		{
			return "invalid country2";
		}

		try {
			if(matchDao.getMatchByCriteria(country1, country2, date) != null)
			{
				return "Match already defined";
			}
		} catch (ParseException e) {
			return "Incorrect date, correct date format: dd.MM.yyyy_HH:mm";
		}


		Match match = new Match();
		match.setCountry1(country1);
		match.setCountry2(country2);
		match.setTime(DateHelper.getDateWithTime(date));
		matchDao.saveMatch(match);

		return "match added";
	}

	@RequestMapping(value = "/addmatchresult", method = RequestMethod.GET,headers="Accept=application/json")
	public String getCountries(
			@RequestParam(value="date") String date,
			@RequestParam(value = "country1") String stringCountry1,
			@RequestParam(value = "country2") String stringCountry2,
			@RequestParam(value = "score") String score) throws ParseException {

		Country country1 = Country.getFromString(stringCountry1);
		if(country1 == null)
		{
			return "invalid country1";
		}

		Country country2 = Country.getFromString(stringCountry2);
		if(country2 == null)
		{
			return "invalid country2";
		}

		if(!score.matches(SCORE_REGEX))
		{
			return "invalid score format. Example score: 2:1";
		}

		Match match = matchDao.getMatchByCriteria(country1, country2, date);

		if(match == null)
		{
			return "match not found";
		}

		score = correctScore(score, country1, match);
		match.setScore(score);
		matchDao.updateeMatch(match);

		return "score to match added";
	}

	private String correctScore(@RequestParam(value = "score") String score, Country country1, Match match) {
		if(country1 != match.getCountry1())
		{
			String scoreRegex = "([0-9]*)(:)([0-9]*)";
			Pattern pattern = Pattern.compile(scoreRegex);
			Matcher matcher = pattern.matcher(score);
			matcher.find();

			String correctScore = matcher.group(3) + matcher.group(2) + matcher.group(1);
			score = correctScore;
		}
		return score;
	}

	@Resource
	public void setMatchDao(MatchDao matchDao) {
		this.matchDao = matchDao;}
}
