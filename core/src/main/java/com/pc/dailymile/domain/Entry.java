package com.pc.dailymile.domain;


/*
 * {
 * "permalink":"http://www.dailymile.com/entries/978880",
 * "message":"",
 * "created_at":"2010-02-14T15:55:52-06:00",
 * "user":{"url":"http://www.dailymile.com/people/jplater",
 *         "display_name":"Jeff",
 *         "photo_url":"http://www.dailymile.com/images/defaults/user_avatar.jpg"
 *         },
 * "workout":{"felt":"good",
 *            "type":"running",
 *            "duration":1645,
 *            "distance":{"units":"miles",
 *                        "value":3.1}
 *            },
 * "id":978880,
 * "comments":[{"body":"Good job!  Welcome to DailyMile!",
 *              "created_at":"2010-02-15T05:54:16-06:00",
 *              "user":{"url":"http://www.dailymile.com/people/chazzerguy",
 *                      "display_name":"Chaz H.",
 *                      "photo_url":"http://s3.dmimg.com/pictures/users/1373/1251135465_avatar.jpg"
 *                      }
 *              }],
 * "likes":[]
 * }
 */

public class Entry {

	private Long id;
	private Workout workout;
	private String message;
	
	public Entry() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
