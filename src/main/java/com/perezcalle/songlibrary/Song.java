package com.perezcalle.songlibrary;

public class Song implements Comparable<Song>{
	private String title;
	private String artist;
	private String albulm;
	private String year;
	
	

	public Song(String t, String art, String alb, String y) {
		this.title = t;
		this.artist = art;
		this.albulm = alb;
		this.year = y;
		// TODO Auto-generated constructor stub
	}
	
	public Song(String t, String art)
	{
		this(t,art,"","");
	}

	public int compartTo(Song s) {
		// TODO Auto-generated method stub
		if(this.title.trim().toUpperCase().compareTo(s.getTitle().trim().toUpperCase()) == 0)
		{
			if(this.artist.trim().toUpperCase().compareTo(s.getArtist().trim().toUpperCase()) == 0)
			{
				return 0;
			}
			else if (this.artist.trim().toUpperCase().compareTo(s.getArtist().trim().toUpperCase()) > 0){
				return 1;
			}
			else {
				return -1;
				
			}
		}
		else if(this.title.trim().toUpperCase().compareTo(s.getTitle().trim().toUpperCase()) > 0) {
			return 1;
		}
		else {
			return -1;
		}
	}

	public String getYear() {
		// TODO Auto-generated method stub
		return this.year;
	}

	public String getArtist() {
		// TODO Auto-generated method stub
		return this.artist;
	}

	public String getTitle() {
		return this.title;
		// TODO Auto-generated method stub
	}
	
	public String getAlbulm() {
		return this.albulm;
		
	}
	
	public void setAlbulm(String a) {
		this.albulm = a;
		
	}
	
	public void setYear(String y) {
		this.year = y;
	}
	
	public String toString() {
		return this.title;
	}

	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
