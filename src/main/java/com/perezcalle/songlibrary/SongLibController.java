package com.perezcalle.songlibrary;

//Still looking over
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import songlib.SongLib;
import songlib.model.Song;

public class SongLibController {
    @FXML
    private TableView<Song> songList;
    @FXML
    private TableColumn<Song, String> TofColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label artist;
    @FXML
    private Label albulm;
    @FXML
    private Label year;

    private SongLib songlib;



    public SongController() {
    }

        @FXML
        public void putTogether()
        {
            TofColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

            showSongList(null);

            songList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSongList(newValue));

        }

        public void setSongLib(Songlib songlib)
        {
            this.songlib = songlib;

            songList.setItems(songlib.getSongList());

            songList.getSelectionModel().select(0);
            
            showSongList(songList.getSelectionModel().getSelectedItem());
        }
        private void showSongList (Song song)
        {
            if(song != null)
            {
                nameLabel.setText(song.getName());
                artist.setText(song.getArtist());
                albulm.setText(song.getAlbulm());
                year.setText(Integer.toString(song.getYear()));
            }
            else 
            {
                nameLabel.setText("");
                artist.setText("");
                year.setText("");
                albulm.setText("");

            }
            }

            @FXML
            private void deleteSong()
            {
                int currentInd = songList.getSelectionModel().getSelectedIndex();
                if(currentInd >= 0)
                {
                    songList.getItems().remove(currentInd);
                    songList.getSelectionModel().select(selectedIndex);
                }
                else 
                {
                    songlib.showErrorDialog();
                }
            }
            @FXML
            private void changeSong()
            {
                Song newSong = new Song();
                boolean[] ind = new boolean[2];
                ind = songlib.showEditDialog(newSong);
                boolean accepted = ind[0];
                boolean deny = ind[1];
                int nextindex = 0;
                if (!deny)
                {
                    if(accepted)
                    {
                        songlib.getSongList().add(newSong);
                        int i=0;
                        while (i < songlib.getSongList().size())
                        {
                            if(newSong == songlib.getSongList().get(i))
                            {
                                nextindex = i;
                                break;
                            }
                            i++;
                        }
                        myComparator comparator = new myComparator();
                        Collections.sort(songlib.getSongList(), comparator);
                        songList.getSelectionModel().select(ind);

                    }
                    else
                    {
                        songlib.showRepeatDialog();
                    }
                }
            }


            @FXML
            private void songEdit()
            {
                Song selectedSong = songList.getSelectionModel().getSelectedItem();
                if (selectedSong != null)
                {
                    boolean[] ind = new boolean[2];
                    ind = songlib.showEditDialog(selectedSong);
                    boolean accepted = ind[0];
                    boolean deny = ind[1];

                    if(!deny)
                    {
                     if(accepted)
                     {
                        showSongList(selectedSong);
                        myComparator comparator = new myComparator();
                        Collections.sort(songlib.getSongList(), comparator);
                     }   
                     else
                     {
                        songlib.showRepeatDialog();
                     }
                    }
                }
            }

        }
