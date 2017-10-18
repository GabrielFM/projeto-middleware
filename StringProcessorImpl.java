
public class StringProcessorImpl implements IStringProcessor {

	@Override
	public String revert(String s) throws Throwable {
		String str_return = new StringBuilder(s).reverse().toString();
		return str_return;
	}

	@Override
	public String toUpper(String s) throws Throwable {
		return s.toUpperCase();
	}

}
