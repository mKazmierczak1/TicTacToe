package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import application.model.Board;
import application.model.GameAI;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GameBoardController implements Initializable {
	
	private int turn;
	private Board board;
	ObservableList<Node> list;
	GameAI AI;
	
	@FXML
	private Label turnCell;
	@FXML
	AnchorPane MyAnchorPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		board = new Board();
		Random r = new Random();
		turn = r.nextInt(2);
		
		if (turn == 1) 
			turnCell.setText("X");
		else
			turnCell.setText("O");
		
		
		list = MyAnchorPane.getChildren();
		AI = new GameAI(turn == 1 ? -1 : 1);
	}
	
	public void playerMove(MouseEvent event) {
		Label cell = (Label)event.getSource();
		setSign(cell, false);
		
		String id = cell.getId();
		String nr = id.substring(id.length() - 1);
		
		
		if (checkIfEnd(event, board.makeMove(Integer.parseInt(nr), turn == 1 ? 1 : -1), false))
			return;
		
		if (list.get(list.size() - 1).getId().equalsIgnoreCase("Single")) {
			int move = AI.makeMove(board.copyBoard(), board.copyControlTab());
			Label cell2 = (Label)list.get(move);
			setSign(cell2, true);
			
			checkIfEnd(event, board.makeMove(move + 1, turn == 1 ? -1 : 1), true);
		}	
		else 
			turn = turn == 0 ? 1 : 0;
	}
	
	private void setSign(Label cell, boolean ifAI) {
		int sign = !ifAI ? turn : (turn == 1 ? 0 : 1);
		
		if (sign == 1) {
			if (!list.get(list.size() - 1).getId().equalsIgnoreCase("Single"))
				turnCell.setText("O");
			
			cell.setText("X");
			cell.setTextFill(Paint.valueOf("blue"));
		}
		else {
			if (!list.get(list.size() - 1).getId().equalsIgnoreCase("Single"))
				turnCell.setText("X");
			
			cell.setText("O");
			cell.setTextFill(Paint.valueOf("red"));
		}
		
		cell.setDisable(true);
	}
	
	private boolean checkIfEnd(MouseEvent event, int response, boolean ifAI) {
		if (response == 1) {
			System.out.println(((!ifAI ? turn : (turn == 1 ? 0 : 1)) == 1 ? "X" : "O") + " won!");
			
			backToMenu(event);
			
			return true;
		}
		else if (response == 2) {
			System.out.println("Draw!");
			
			backToMenu(event);
			
			return true;
		}
		
		return false;
	}
	
	private void backToMenu(MouseEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/Entry.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
