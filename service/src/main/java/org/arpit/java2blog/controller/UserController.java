package org.arpit.java2blog.controller;

import com.euro.typer.data.source.dao.MatchDao;
import com.euro.typer.data.source.dao.PlayerTypeDao;
import com.euro.typer.data.source.dao.UserDao;
import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import com.euro.typer.data.source.entity.PlayerType;
import org.arpit.java2blog.user.MatchScore;
import org.arpit.java2blog.user.UserMatchRS;
import org.arpit.java2blog.user.UserTypesRS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

	private UserDao userDao;
	private PlayerTypeDao playerTypeDao;
	private MatchDao matchDao;

	@RequestMapping(value = "/addUser", method = RequestMethod.GET, headers = "Accept=application/json")
	public String addUser(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "mail") String email) {

		if (username.length() < 3) {
			return "Invalid username. Min. length is 3";
		}

		if (password.length() < 5) {
			return "Invalid password, Min. length is 5";
		}

		Player existingPlayer = userDao.getUserByUsername(username);
		if (existingPlayer != null) {
			return "user already exist";
		}

		Player newPlayer = new Player();
		newPlayer.setUsername(username);
		newPlayer.setEmail(email);
		newPlayer.setPassword(password);

		userDao.saveUser(newPlayer);

		return "User added successfully";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, headers = "Accept=application/json")
	public String addUser(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		Player player = userDao.getUserByUsername(username);
		if(player == null)
		{
			return "Invalid username";
		}

		if(!player.getPassword().equals(password))
		{
			return "Invalid password";
		}

		return "Log In";
	}

	@RequestMapping(value = "/userTypes", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserTypesRS getUserTypes(
			@RequestParam(value = "username") String username) {

		Player player = userDao.getUserByUsername(username);
		if(player == null)
		{
			UserTypesRS errorRS = new UserTypesRS();
			errorRS.setMessage("Invalid Username");
			return errorRS;
		}

		List<PlayerType> playerTypeList = playerTypeDao.getByPlayer(player);
		UserTypesRS validRS = new UserTypesRS();
		validRS.setPlayerTypes(playerTypeList);
		return validRS;
	}

	@RequestMapping(value = "/getUserMatches", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserMatchRS getUserMatches(
			@RequestParam(value = "username") String username) {

		Player player = userDao.getUserByUsername(username);
		if(player == null)
		{
			UserMatchRS errorRS = new UserMatchRS();
			errorRS.setErrorMessage("Invalid Username");
			return errorRS;
		}

		List<Match> matches = matchDao.getAllMatches();
		List<MatchScore> matchScores = new ArrayList<>();

		for(Match match : matches)
		{
			PlayerType playerType = playerTypeDao.getByPlayerAndMatch(player, match);

			MatchScore matchScore = new MatchScore();
			matchScore.setMatch(match);
			if(playerType != null) {
				matchScore.setType(playerType.getTypedScore());
			}
			matchScores.add(matchScore);
		}

		UserMatchRS userMatchRS = new UserMatchRS();
		userMatchRS.setPlayer(player);
		userMatchRS.setMatchScore(matchScores);

		return userMatchRS;
	}


	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setPlayerTypeDao(PlayerTypeDao playerTypeDao) {
		this.playerTypeDao = playerTypeDao;
	}

	@Resource
	public void setMatchDao(MatchDao matchDao) {
		this.matchDao = matchDao;
	}
}
