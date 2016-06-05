package org.arpit.java2blog.controller;

import com.euro.typer.data.source.dao.MatchDao;
import com.euro.typer.data.source.dao.PlayerTypeDao;
import com.euro.typer.data.source.dao.UserDao;
import com.euro.typer.data.source.entity.Country;
import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import com.euro.typer.data.source.entity.PlayerType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class TypeController {

	private final static String SCORE_REGEX = "^[0-9]*:[0-9]*$";

	private UserDao userDao;
	@RequestMapping(value = "/type", method = RequestMethod.GET,headers="Accept=application/json")
//	public List<Country> getCountries(
	public String getCountries(
			@RequestParam(value="username") String username,
			@RequestParam(value="date") String date,
			@RequestParam(value = "country1") String stringCountry1,
			@RequestParam(value = "country2") String stringCountry2,
			@RequestParam(value = "score") String score)
	{

		Player requestedPlayer = userDao.getUserByUsername(username);
		if(requestedPlayer == null)
		{
			return "Invalid username";
		}


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

		Match match = null;
		try {
			match = matchDao.getMatchByCriteria(country1, country2, date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "Invalid date format, poprawny format: dd.MM";
		}
		if(match == null)
		{
			return "match not found";
		}

		Date hourLater = DateUtils.addHours(new Date(), 1);
		if(hourLater.after(match.getTime()))
		{
			return "Too late for type to this match";
		}

		if(!score.matches(SCORE_REGEX))
		{
			return "invalid score format. Example score: 2:1";
		}

		score = correctScore(score, country1, match);

		PlayerType playerType = playerTypeDao.getByPlayerAndMatch(requestedPlayer, match);
		if(playerType != null)
		{
			playerType.setTypedScore(score);
			playerTypeDao.updatePlayerType(playerType);
			return "Type for this match already exists";
		}
		else {
			playerType = new PlayerType();
			playerType.setMatch(match);
			playerType.setPlayer(requestedPlayer);
			playerType.setTypedScore(score);
			playerTypeDao.savePlayerType(playerType);
			return "Type saved Successfully";
		}
	}
	private MatchDao matchDao;

	private PlayerTypeDao playerTypeDao;

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
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setMatchDao(MatchDao matchDao) {
		this.matchDao = matchDao;
	}

	@Resource
	public void setPlayerTypeDao(PlayerTypeDao playerTypeDao) {
		this.playerTypeDao = playerTypeDao;
	}
}
