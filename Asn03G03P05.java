import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// A Tree node
class Node
{
	char ch;
	int frequancy;
	Node left = null, right = null;

	Node(char ch, int frequancy)
	{
		this.ch = ch;
		this.frequancy = frequancy;
	}

	public Node(char ch, int frequancy, Node left, Node right)
	{
		this.ch = ch;
		this.frequancy = frequancy;
		this.left = left;
		this.right = right;
	}
};

class Asn03G03P05
{
	// traverse the Huffman Tree and store Huffman Codes in map
	public static void encode(Node root, String str, Map<Character, String> huffmanCode)
	{
		if (root == null)
			return;
		if (root.left == null && root.right == null) {
			huffmanCode.put(root.ch, str);
		}
		encode(root.left, str + "0", huffmanCode);
		encode(root.right, str + "1", huffmanCode);
	}

	public static int decode(Node root, int index, StringBuilder sb)		// traverse the Huffman Tree and decode the encoded string
	{
		if (root == null)
			return index;
		if (root.left == null && root.right == null)
		{
			System.out.print(root.ch);
			return index;
		}
		index++;
		if (sb.charAt(index) == '0')
			index = decode(root.left, index, sb);
		else
			index = decode(root.right, index, sb);

		return index;
	}
	
	public static void buildHuffmanTree(String str)				// Builds Huffman Tree and huffmanCode and decode given input text
	{
		Map<Character, Integer> freq = new HashMap<>();				// count frequency of appearance of each character and store in a map
		for (int i = 0 ; i < str.length(); i++) {
			if (!freq.containsKey(str.charAt(i))) {
				freq.put(str.charAt(i), 0);
			}
			freq.put(str.charAt(i), freq.get(str.charAt(i)) + 1);
		}
		PriorityQueue<Node> pq = new PriorityQueue<>((l, r) -> l.frequancy - r.frequancy);		// Create a priority queue to store live nodes of Huffman tree
		for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
			pq.add(new Node(entry.getKey(), entry.getValue()));
		}
		while (pq.size() != 1)									// do till there is more than one node in the queue
		{
			Node left = pq.poll();
			Node right = pq.poll();
			int sum = left.frequancy + right.frequancy;					// Create a new internal node with these two nodes as children and
			pq.add(new Node('\0', sum, left, right));			//with frequency equal to the sum of the two nodes frequencies. Add the new node to the priority queue.
		}
		Node root = pq.peek();
		
		Map<Character, String> huffmanCode = new HashMap<>();	// traverse the Huffman tree and store the Huffman codes in a map
		encode(root, "", huffmanCode);
		System.out.println("Huffman Codes are :\n");
		for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		System.out.println("\nOriginal string was :\n" + str);
		StringBuilder sb = new StringBuilder();							// print encoded string
		for (int i = 0 ; i < str.length(); i++) {
			sb.append(huffmanCode.get(str.charAt(i)));
		}

		System.out.println("\nEncoded string is :\n" + sb);
		// traverse the Huffman Tree again and this time decode the encoded string
		int index = -1;
		System.out.println("\nDecoded string is:");
		while (index < sb.length() - 2) {
			index = decode(root, index, sb);
		}
	}

	public static void main(String[] args)
	{
		String str = null;				//give the input in string form using " " otherwise we don't get the required output
	     for(int i = 0; i<args.length; i++) {
	    	str = args[i];
	      } 
		buildHuffmanTree(str);
	}
}