public class Main {
	
	public static void main(String[] args) {
		
		Network net = Network.getInstance();

		User takis = new User("takis");
		takis.setEmail("takaros");
		net.addUser(takis);
		User nikos = new User("nikos");
		nikos.setEmail("nikolas");
		net.addUser(nikos);
		User ioannic = new User("ioannic");
		ioannic.setEmail("giannakis");
		net.addUser(ioannic);
		User mixalioc = new User("mixalioc");
		mixalioc.setEmail("mikalis");
		net.addUser(mixalioc);
		
		net.userSign();
		
	}
}
