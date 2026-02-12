package alda.t4.hash;

public class DoubleHashingProbingHashTable<T> extends ProbingHashTable<T> {

	/*
	 * Denna metod ska skrivas klart. Den ska använda bokens förslag på andra
	 * hashalgoritm: f(i) = i * hash2(x), där hash2(x) = R - (x mod R) och R är
	 * ett primtal mindre än tabellstorleken.
	 */
	@Override
	protected int findPos(T x) {
		int val = myhash(x);

		int otherVal = smallerPrimeThanCapacity() - (myhash(x) % smallerPrimeThanCapacity());

		for(int i = 0; i < capacity(); i++) {
			int sum = (val + i * otherVal) % capacity();
			if(!continueProbing(sum, x)) {
				return sum;
			}
		}

		return -1;
	}

	/*
	 * Denna metod ger ett primtal mindre än tabellens storlek. Detta primtal ska
	 * användas i metoden ovan.
	 */
	protected int smallerPrimeThanCapacity() {
		int n = capacity() - 2;
		while (!isPrime(n)) {
			n -= 2;
		}
		return n;
	}

}
