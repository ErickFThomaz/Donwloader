package downloader.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ProgressObservableInputStream extends FilterInputStream {

	private final long size;
	private double readCount = 0.0;


	private final long kilo = 1024;
	private final long mega = kilo * kilo;
	private final long giga = mega * kilo;
	private final long tera = giga * kilo;

	public double getPercentage() {

		double count = ((readCount * 100.0) / size);

		return round(count, 1);
	}

	public ProgressObservableInputStream(InputStream in, long size) {
		super(in);
		this.size = size;
	}

	@Override
	public int read() throws IOException {

		int count = in.read();

		if (count >= 0) {
			readCount++;
		}

		return count;
	}

	@Override
	public int read(byte[] b) throws IOException {

		int count = in.read(b);

		if (count > 0) {
			readCount += count;
		}

		return count;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int count = in.read(b, off, len);

		if (count > 0) {
			readCount += count;
		}

		return count;
	}

	@Override
	public long skip(long n) throws IOException {

		long count = in.skip(n);

		if (count > 0) {
			readCount += count;
		}

		return count;
	}


	@Override
	public synchronized void reset() throws IOException {
		in.reset();

		readCount = size - in.available();

	}

	public String getSize() {
		String s = "";
		double kb = (double) size / kilo;
		double mb = kb / kilo;
		double gb = mb / kilo;
		double tb = gb / kilo;
		if (size < kilo) {
			s = size + " Bytes";
		} else if (size >= kilo && size < mega) {
			s = String.format("%.2f", kb) + " KB";
		} else if (size >= mega && size < giga) {
			s = String.format("%.2f", mb) + " MB";
		} else if (size >= giga && size < tera) {
			s = String.format("%.2f", gb) + " GB";
		} else if (size >= tera) {
			s = String.format("%.2f", tb) + " TB";
		}
		return s;
	}

	public String getSize(long size) {
		String s = "";
		double kb = (double) size / kilo;
		double mb = kb / kilo;
		double gb = mb / kilo;
		double tb = gb / kilo;
		if (size < kilo) {
			s = size + " Bytes";
		} else if (size >= kilo && size < mega) {
			s = String.format("%.2f", kb) + " KB";
		} else if (size >= mega && size < giga) {
			s = String.format("%.2f", mb) + " MB";
		} else if (size >= giga && size < tera) {
			s = String.format("%.2f", gb) + " GB";
		} else if (size >= tera) {
			s = String.format("%.2f", tb) + " TB";
		}
		return s;
	}

	private static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
