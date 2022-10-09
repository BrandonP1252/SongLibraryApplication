package com.perezcalle.songlibrary;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class MainSceneController {
	@FXML
	private ListView<Song> songList;
	@FXML
	private TextField TitleAdd;
	@FXML
	private TextField ArtistAdd;
	@FXML
	private TextField yearAdd;
	@FXML
	private TextField albumAdd;
	@FXML
	private TextField titleEdit;
	@FXML
	private TextField artistEdit;
	@FXML
	private TextField albumEdit;
	@FXML
	private TextField yearEdit;

	private ObservableList<Song> obsList;

	public void start(Stage primaryStage) {
		File data = new File("songlists.txt");
		obsList = FXCollections.observableArrayList();
		if (data.exists() && !data.isDirectory()) {
			try {
				Scanner fileIn = new Scanner(data);

				int lines = 0;
				while (fileIn.hasNextLine()) {
					lines++;
					fileIn.nextLine();
				}
				fileIn.close();
				fileIn = new Scanner(data);
				lines -= 2;
				fileIn.nextLine();
				fileIn.nextLine();
				if (lines % 4 == 0) {
					for (int i = 0; i < lines; i += 4) {
						obsList.add(new Song(fileIn.nextLine(), fileIn.nextLine(), fileIn.nextLine(), fileIn.nextLine()));

					}

					FXCollections.sort(obsList);
				}
				fileIn.close();
			} catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("File Error");
				alert.setContentText("File does not exist");
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		songList.setItems(obsList);
		if (!obsList.isEmpty()) {
			songList.getSelectionModel().selectFirst();

		}

		showSongDetails();

		songList.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldValue, newValue) -> showSongDetails());

		primaryStage.setOnCloseRequest(event -> {
			PrintWriter write;
			try {
				File file = new File("songlists.txt");
				file.createNewFile();
				write = new PrintWriter(file);
				write.println("Song List");
				write.println("\n");
				for (int i = 0; i < obsList.size(); i++) {
					write.print(obsList.get(i).getTitle() + (" "));
					write.print(obsList.get(i).getArtist() + (" "));
					write.print(obsList.get(i).getalbum() + (" "));
					write.print(obsList.get(i).getYear());
					if (i != obsList.size() - 1) {
						write.println("");
					}
				}
				write.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		});

		TitleAdd.setText("");
		ArtistAdd.setText("");
		albumAdd.setText("");
		yearAdd.setText("");
	}


	// Event Listener on Button.onAction
	@FXML
	private void deleteSong(ActionEvent event) {
		if (obsList.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Nothing to delete");
			alert.setContentText("Song list is empty");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("WARNING");
			alert.setHeaderText("You are about to delete the selected song");
			alert.setContentText("Are you sure?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				int selectedIndex = songList.getSelectionModel().getSelectedIndex();
				obsList.remove(selectedIndex);

				if (obsList.isEmpty()) {
					titleEdit.setText("");
					artistEdit.setText("");
					albumEdit.setText("");
					yearEdit.setText("");
				} else if (selectedIndex == obsList.size() - 1) {
					songList.getSelectionModel().select(selectedIndex--);
				} else {
					songList.getSelectionModel().select(selectedIndex++);

				}
			} else {
				return;
			}
		}
	}

	// TODO Autogenerated
	// Event Listener on Button.onAction
	@FXML
	private void songAdd(ActionEvent event) {
		if (albumAdd.getText().compareTo("") == 0) {
			albumAdd.setText("");
		}
		if (yearAdd.getText().compareTo("") == 0) {
			yearAdd.setText("");
		}
		// added trim to take out leading/trailing spaces
		Song tempSong = new Song(TitleAdd.getText().trim(), ArtistAdd.getText().trim(), albumAdd.getText().trim(), yearAdd.getText().trim());

		add(tempSong);
		// TODO Autogenerated
	}

	private int add(Song tempSong) {
		String artist, title;

		artist = tempSong.getArtist();
		title = tempSong.getTitle();

		if (duplicate(tempSong, obsList)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ATTENTION");
			alert.setHeaderText("Duplicate");
			alert.showAndWait();
			return -1;
		}
		else if (artist.compareTo("") == 0 || title.compareTo("") == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ATTENTION");
			alert.setHeaderText("Missing input");
			alert.setContentText("Title and Artist input must not be empty");
			alert.showAndWait();
			return -1;
		}
			else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("ATTENTION");
			alert.setHeaderText("Adding a new song");
			alert.setContentText("You are about to add a new song: \n" + title + " by: " + artist + "\nAre you sure you want to add?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				obsList.add(tempSong);
				sortList(obsList);
				TitleAdd.setText("");
				ArtistAdd.setText("");
				albumAdd.setText("");
				yearAdd.setText("");
				return 0;
			} else {
				showSongDetails();
			}


		}
		return 0;
	}


	// Event Listener on Button.onAction
	@FXML
	private void songEdit(ActionEvent event) {

		Song selectedSong = (Song) songList.getSelectionModel().getSelectedItem();
		Song tempSong = new Song(titleEdit.getText().trim(), artistEdit.getText().trim(), albumEdit.getText().trim(), yearEdit.getText().trim());

		if (selectedSong.getTitle().compareTo(tempSong.getTitle()) == 0 && selectedSong.getArtist().compareTo(tempSong.getArtist()) == 0) {
			((Song) songList.getSelectionModel().getSelectedItem()).setalbum(tempSong.getalbum());
			((Song) songList.getSelectionModel().getSelectedItem()).setYear(tempSong.getYear());
		} else {
			obsList.remove(songList.getSelectionModel().getSelectedIndex());
			if (add(tempSong) == -1) {
				add(selectedSong);
			}
			// TODO Autogenerated
		}
	}

	private void showSongDetails() {
		if (songList.getSelectionModel().getSelectedIndex() < 0) {
			return;
		}

		Song song = (Song) songList.getSelectionModel().getSelectedItem();
		titleEdit.setText(song.getTitle());
		artistEdit.setText(song.getArtist());
		albumEdit.setText(song.getalbum());
		yearEdit.setText(song.getYear());
	}

	private void sortList(ObservableList<Song> obsList) {
		Comparator<Song> comparator = Comparator.comparing(Song::getTitle).thenComparing(Song::getArtist);
		obsList.sort(comparator);

	}

	private boolean duplicate(Song song, ObservableList<Song> obsList) {
		String title = song.getTitle().toLowerCase();
		String artist = song.getArtist().toLowerCase();
		for(Song compare:obsList) {
			if (title.compareTo(compare.getTitle().toLowerCase()) == 0 && artist.compareTo(compare.getArtist().toLowerCase()) == 0) {
				return true;
			}
		}
		return false;
	}
}
