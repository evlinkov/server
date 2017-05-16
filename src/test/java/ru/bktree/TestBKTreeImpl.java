package ru.bktree;

import org.junit.Test;
import org.junit.Ignore;
import java.util.HashMap;
import ru.distance.LevenshteinDistance;
import static org.junit.Assert.assertEquals;

public class TestBKTreeImpl {

	@Test
	@Ignore
	public void testTree() {
		String[] wordList = new String[] {
				"remote",
				"barn",
				"book",
				"glass",
				"chair",
				"closet",
				"skull",
				"giraffe",
				"boat",
				"soda"
		};

		BKTreeImpl bkTree = new BKTreeImpl(new LevenshteinDistance(), null);

		for (String word : wordList) {
			bkTree.add(word);
		}

		HashMap<String, Integer> queryMap = bkTree.query("bark", 2);
		assertEquals(queryMap.size(), 2);
		assertEquals(queryMap.get("barn").toString(), "1");
		assertEquals(queryMap.get("book").toString(), "2");

		HashMap<String, Integer> searchTerm1 = bkTree.findBestWordMatchWithDistance("temotw");
		assertEquals(searchTerm1.size(), 1);
		assertEquals(searchTerm1.get("remote").toString(), "2");

		HashMap<String, Integer> searchTerm2 = bkTree.findBestWordMatchWithDistance("garage");
		assertEquals(searchTerm2.size(), 1);
		assertEquals(searchTerm2.get("giraffe").toString(), "3");
	}

}
