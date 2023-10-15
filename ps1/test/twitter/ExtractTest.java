/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("Expected start", d1, timespan.getStart());
        assertEquals("Expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("Expected start", d1, timespan.getStart());
        assertEquals("Expected end", d1, timespan.getEnd());
    }



    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("Expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweetWithMentions = new Tweet(3, "user3", "Mentioning @user1 and @user2 here.", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweetWithMentions));
        
        assertTrue("Expected non-empty set", mentionedUsers.contains("user1"));
        assertTrue("Expected non-empty set", mentionedUsers.contains("user2"));
        assertEquals("Expected 2 mentions", 2, mentionedUsers.size());
    }
}
