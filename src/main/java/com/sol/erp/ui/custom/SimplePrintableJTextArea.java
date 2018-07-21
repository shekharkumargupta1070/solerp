package com.sol.erp.ui.custom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class SimplePrintableJTextArea extends JTextArea implements Printable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jobName = "Print Job for " + System.getProperty("user.name");

	public SimplePrintableJTextArea() {
	}

	public SimplePrintableJTextArea(String paramString) {
		super(paramString);
	}

	int inchesToPage(double paramDouble) {
		return (int) (paramDouble * 72.0D);
	}

	int left_margin = inchesToPage(0.5D);
	int right_margin = inchesToPage(0.5D);
	int top_margin = inchesToPage(0.5D);
	int bottom_margin = inchesToPage(0.5D);

	public boolean print() {
		PrinterJob localPrinterJob = PrinterJob.getPrinterJob();

		localPrinterJob.setPrintable(this);
		localPrinterJob.setJobName(this.jobName);

		HashPrintRequestAttributeSet localHashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
		localHashPrintRequestAttributeSet.add(OrientationRequested.PORTRAIT);
		localHashPrintRequestAttributeSet.add(Chromaticity.COLOR);
		localHashPrintRequestAttributeSet.add(Chromaticity.MONOCHROME);
		localHashPrintRequestAttributeSet.add(PrintQuality.NORMAL);
		localHashPrintRequestAttributeSet.add(PrintQuality.DRAFT);
		localHashPrintRequestAttributeSet.add(PrintQuality.HIGH);

		localHashPrintRequestAttributeSet.add(new MediaPrintableArea(0.25F, 0.25F, 8.0F, 10.5F, 25400));

		if (localPrinterJob.printDialog()) {
			try {
				localPrinterJob.print(localHashPrintRequestAttributeSet);
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return true;
	}

	public int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) throws PrinterException {
		Graphics2D localGraphics2D = (Graphics2D) paramGraphics;

		localGraphics2D.setFont(getFont().deriveFont(9.0F));

		int i = (int) paramPageFormat.getImageableHeight();
		paramPageFormat.getImageableWidth();
		int k = localGraphics2D.getFontMetrics().getHeight() - localGraphics2D.getFontMetrics().getLeading() / 2;

		int m = (i - this.top_margin - this.bottom_margin) / k;

		int n = m * paramInt;

		if (n > getLineCount()) {
			return 1;
		}

		getLineCount();

		int i2 = n + m;

		int i3 = this.top_margin;

		getLineCount();

		localGraphics2D.translate(paramPageFormat.getImageableX(), paramPageFormat.getImageableY());

		for (int i5 = n; i5 < i2; i5++) {
			try {
				String str = getText(getLineStartOffset(i5), getLineEndOffset(i5) - getLineStartOffset(i5));

				localGraphics2D.drawString(str, this.left_margin, i3);
			} catch (BadLocationException localBadLocationException) {
			}

			i3 += k;
			if (i3 > i - this.bottom_margin) {
				break;
			}
		}

		return 0;
	}
}