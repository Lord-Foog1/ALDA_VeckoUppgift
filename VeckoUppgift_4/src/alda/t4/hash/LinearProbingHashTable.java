package alda.t4.hash;

public class LinearProbingHashTable<T> extends ProbingHashTable<T> {

	/*
	 * Denna metod ska skrivas klart. Den ska använda linjär sondering och hela tiden öka med ett.
	 */
	@Override
	protected int findPos(T x) {

		int val = myhash(x);

		for(int i = 0; i < capacity(); i++) {
			if(!continueProbing(val, x)) {
				return val;
			}
			val = (val +1) % capacity();
		}

		return -1;
	}

}
