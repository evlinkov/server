package ru.bktree;

import java.util.List;
import ru.dao.ProductDao;
import java.util.HashMap;
import ru.entities.Product;
import javax.inject.Inject;
import ru.distance.Distance;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class BKTreeImpl implements BKTree  {

	@Inject
	Distance distance;
    @Inject
    ProductDao productDao;

	private Node root;
	private String bestTerm;
    private HashMap<String, Integer> matches;

    public BKTreeImpl() { }

	@PostConstruct
    public void initialization() {
	    root = null;
	    List<Product> products = productDao.getAllProducts();
	    for (Product product : products) {
	        String name = product.getName();
	        add(name);
        }
    }

	public BKTreeImpl(Distance distance) {
		root = null;
		this.distance = distance;
	}

	public void add(String term) {
		if(root != null) {
			root.add(term);
		}
		else {
			root = new Node(term);
		}
	}

	@Override
	public HashMap<String, Integer> query(String searchObject, int threshold) {
		matches = new HashMap<>();
		root.query(searchObject, threshold, matches);
		return matches;
	}

	@Override
	public HashMap<String,Integer> findBestWordMatchWithDistance(String term) {
		int distance = root.findBestMatch(term, Integer.MAX_VALUE);
		HashMap<String, Integer> returnMap = new HashMap<String, Integer>();
		returnMap.put(root.getBestTerm(), distance);
		return returnMap;
	}

	private class Node {

		String term;
		HashMap<Integer, Node> children;

		public Node(String term) {
			this.term = term;
			children = new HashMap<Integer, Node>();
		}

		public void add(String term) {
			int score = distance.getDistance(term, this.term);

			Node child = children.get(score);
			if(child != null) {
				child.add(term);
			}
			else {
				children.put(score, new Node(term));
			}
		}

		public int findBestMatch(String term, int bestDistance) {

			int distanceAtNode = distance.getDistance(term, this.term);

			if(distanceAtNode < bestDistance) {
				bestDistance = distanceAtNode;
				bestTerm = this.term;
			}
			
			int possibleBest = bestDistance;

			for (Integer score : children.keySet()) {
				if(score < distanceAtNode + bestDistance ) {
					possibleBest = children.get(score).findBestMatch(term, bestDistance);
					if(possibleBest < bestDistance) {
						bestDistance = possibleBest;
					}
				}
			}
			return bestDistance;
		}
		
		public String getBestTerm() {
			return bestTerm;
		}

		public void query(String term, int threshold, HashMap<String, Integer> collected) {
			int distanceAtNode = distance.getDistance(term, this.term);

			if(distanceAtNode == threshold) {
				collected.put(this.term, distanceAtNode);
				return;
			}

			if(distanceAtNode < threshold) {
				collected.put(this.term, distanceAtNode);
			}

			for (int score = distanceAtNode-threshold; score <= threshold+distanceAtNode; score++) {
				Node child = children.get(score);
				if(child != null) {
					child.query(term, threshold, collected);
				}
			}
		}

	}

}
