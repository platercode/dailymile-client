package com.pc.dailymile.domain;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.pc.dailymile.utils.DailyMileUtil;

public class UserTest {

    private static final String SAMPLE_STREAM =
        "{\n"
            + "  \"entries\": [\n"
            + "    {\n"
            + "      \"id\": 232323232,\n"
            + "      \"message\": \"great run\",\n"
            + "      \"workout\": {\n"
            + "        \"type\": \"running\",\n"
            + "        \"distance\": {\n"
            + "          \"value\": 5.5,\n"
            + "          \"units\": \"miles\"\n"
            + "        },\n"
            + "        \"duration\": 23252332432,\n"
            + "        \"felt\": \"great\",\n"
            + "        \"calories\": 421\n"
            + "      },\n"
            + "            \"user\": {\n"
            + "        \"display_name\": \"Ben W.\",\n"
            + "        \"url\": \"http//www.dailymile.com/people/ben\",\n"
            + "        \"photo_url\": \"http://media.dailymile.com/pictures/2.jpg\"\n"
            + "      },\n"
            + "      \"permalink\": \"http://www.dailymile.com/entries/23131323\",\n"
            + "      \"created_at\": \"2010-07-21T02:20:24-05:00\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"id\": 232323233,\n"
            + "      \"message\": \"hi, i'm just a note\",\n"
            + "      \"user\": {\n"
            + "        \"display_name\": \"Ben W.\",\n"
            + "        \"url\": \"http//www.dailymile.com/people/ben\",\n"
            + "        \"photo_url\": \"http://media.dailymile.com/pictures/2.jpg\"\n"
            + "      },\n"
            + "      \"permalink\": \"http://www.dailymile.com/entries/23131322\",\n"
            + "      \"created_at\": \"2010-07-23T02:20:24-05:00\",\n"
            + "      \"comments\": [\n"
            + "        {\n"
            + "          \"body\": \"wow, awesome note\",\n"
            + "          \"created_at\": \"2010-07-23T20:57:08-05:00\",\n"
            + "          \"user\": {\n"
            + "            \"display_name\": \"Ben W.\",\n"
            + "            \"url\": \"http//www.dailymile.com/people/ben\",\n"
            + "            \"photo_url\": \"http://media.dailymile.com/pictures/2.jpg\"\n"
            + "          }\n"
            + "        }\n"
            + "      ],\n"
            + "      \"likes\": [\n"
            + "        {\n"
            + "          \"created_at\": \"2010-07-23T20:57:08-05:00\",\n"
            + "          \"user\": {\n"
            + "            \"display_name\": \"Ben W.\",\n"
            + "            \"url\": \"http//www.dailymile.com/people/ben\",\n"
            + "            \"photo_url\": \"http://media.dailymile.com/pictures/2.jpg\"\n"
            + "          }\n"
            + "        }\n"
            + "      ]\n"
            + "    },\n"
            + "    { \n"
            + "      \"id\": 232323234,\n"
            + "      \"message\": \"beautiful photo i took along my run\",\n"
            + "      \"media\": [\n"
            + "        {\n"
            + "          \"content\": {\n"
            + "            \"type\": \"image\",\n"
            + "            \"url\": \"http://s3.amazonaws.com/someimg.jpg\",\n"
            + "            \"width\": 150,\n"
            + "            \"height\": 150\n"
            + "          },\n"
            + "          \"preview\": {\n"
            + "            \"type\": \"image\",\n"
            + "            \"url\": \"http://s3.amazonaws.com/preview.jpg\",\n"
            + "            \"height\": 75,\n"
            + "            \"width\": 75\n"
            + "          }\n"
            + "        }\n"
            + "      ],\n"
            + "      \"user\": {\n"
            + "        \"display_name\": \"Ben W.\",\n"
            + "        \"url\": \"http//www.dailymile.com/people/ben\",\n"
            + "        \"photo_url\": \"http://media.dailymile.com/pictures/2.jpg\"\n"
            + "      },\n"
            + "      \"permalink\": \"http://www.dailymile.com/entries/23131322\",\n"
            + "      \"created_at\": \"2010-07-22T02:20:24-05:00\"\n"
            + "    }, \n"
            + "    { \n"
            + "      \"id\": 232323234,\n"
            + "      \"message\": \"awesome video i took today, really cool footage\",\n"
            + "      \"media\": [\n"
            + "        {\n"
            + "          \"content\": {\n"
            + "            \"type\": \"video\",\n"
            + "            \"url\": \"http://s3.amazonaws.com/video.mp4\"\n"
            + "          },\n"
            + "          \"preview\": {\n"
            + "            \"type\": \"image\",\n"
            + "            \"url\": \"http://s3.amazonaws.com/video_thumbnail.jpg\",\n"
            + "            \"height\": 120,\n"
            + "            \"width\": 90\n"
            + "          }\n"
            + "        }\n"
            + "      ],\n"
            + "      \"user\": {\n"
            + "        \"display_name\": \"Ben W.\",\n"
            + "        \"url\": \"http//www.dailymile.com/people/ben\",\n"
            + "        \"photo_url\": \"http://media.dailymile.com/pictures/2.jpg\"\n"
            + "      },\n"
            + "      \"permalink\": \"http://www.dailymile.com/entries/23131322\",\n"
            + "      \"created_at\": \"2010-07-20T02:20:24-05:00\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    @Test
    public void testMarshallStreamJSON() {
        UserStream stream = DailyMileUtil.getGson().fromJson(SAMPLE_STREAM, UserStream.class);
        Set<Entry> entries = stream.getEntries();
        assertEquals(4, entries.size());

        User u = new User();
        u.setName("Ben W.");
        u.setUrl("http//www.dailymile.com/people/ben");
        u.setImageUrl("http://media.dailymile.com/pictures/2.jpg");
        assertEquals(u, entries.toArray(new Entry[entries.size()])[0].getUser());
    }

    /**
     * For whatever reason the stream entries come back in some bizarre,
     * non-intuitive order. This test ensures that the entries get sorted in
     * time descending order after being retrieved from the service.
     */
    @Test
    public void testStreamSorting() {
        UserStream stream = DailyMileUtil.getGson().fromJson(SAMPLE_STREAM, UserStream.class);
        Set<Entry> entries = stream.getEntries();
        assertEquals(4, entries.size());

        Entry first = (Entry) entries.toArray()[0];
        assertEquals(Long.valueOf("232323233"), first.getId());
    }
}
