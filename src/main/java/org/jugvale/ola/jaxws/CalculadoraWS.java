package org.jugvale.ola.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class CalculadoraWS {

    @WebMethod
	public double fazerOp(@WebParam(name = "num1") double a
						, @WebParam(name = "num2") double b
						, @WebParam(name = "op") String op) {
		switch (op) {
		case "+":
			return a + b;
		case "-":
			return a - b;
		case "*":
			return a * b;
		case "/":
			return a / b;
		default:
			throw new IllegalArgumentException("Operação '" + op
					+ "' não reconhecida. Informa '+', '-', '*' ou '/'.");
		}
	}

	@WebMethod
	public String sayHello(String name){
    	return "Say Hello to " + name;
	}

	@WebMethod
	public String hexTransform(String hex){
		//HEX to RGB
		int r = Integer.valueOf( hex.substring( 1, 3 ), 16 );
		int g = Integer.valueOf( hex.substring( 3, 5 ), 16 );
		int b = Integer.valueOf( hex.substring( 5, 7 ), 16 );

		//RGB to CMYK
		String cmyk = toCMYK(r,g,b);

		//RGB to HSV
		String hsv = toHSV(r,g,b);

		//RGB to HSL
		String hsl = toHSL(r,g,b);

		return "RGB: " + r + ", " + g + ", " + b + "\n CMYK: " + cmyk + "\n HSV: " + hsv + "\n HSL: " + hsl;

	}

	private String toCMYK(int r, int g, int b){
		double pr = r / 255.0 * 100;
		double pg = g / 255.0 * 100;
		double pb = b / 255.0 * 100;

		double nk = 100 - Math.max(Math.max(pr, pg), pb);

		if(nk == 100){
			return "0, 0, 0, 100";
		}else{
			double c = (100 - pr - nk) / (100 - nk) * 100;
			double m = (100 - pg - nk) / (100 - nk) * 100;
			double y = (100 - pb - nk) / (100 - nk) * 100;
			double k = nk;
			return (int)c + "%, " + (int)m + "%, " + (int)y + "%, " + (int)k + "%";
		}
	}

	private String toHSV(int r, int g, int b){
		double h, s, v;

		double min, max, delta;

		min = Math.min(Math.min(r, g), b);
		max = Math.max(Math.max(r, g), b);

		v = max;

		delta = max - min;

		// S
		if (max != 0)
			s = delta / max;
		else {
			s = 0;
			h = -1;
			return (int)h + "°, "+ (int)s + "%, " + (int)v + "%";
		}

		// H
		if (r == max)
			h = (g - b) / delta;
		else if (g == max)
			h = 2 + (b - r) / delta;
		else
			h = 4 + (r - g) / delta;

		h *= 60;

		if (h < 0)
			h += 360;

		h = h * 1.0;
		s = s * 100.0;
		v = (v / 256.0) * 100.0;
		return (int)h + "°, "+ (int)s + "%, " + (int)v + "%";
	}

	private String toHSL(int r, int g, int b) {
		double pr = r/255.0;
		double pg = g/255.0;
		double pb = b/255.0;

		double cmin = Math.min(Math.min(pr,pg),pb);
		double cmax = Math.max(Math.max(pr,pg),pb);
		double delta = cmax - cmin;

		double h = 0;
		double s = 0;
		double l = 0;

		if(delta == 0){
			h = 0;
		}else if(cmax == pr){
			h = ((pg - pb) / delta) % 6;
		}else if(cmax == pg){
			h = (pb - pr) / delta + 2;
		}else{
			h = (pr - pg) / delta + 4;
		}

		h = Math.round(h * 60);

		if(h < 0){
			h += 360;
		}

		l = (cmax + cmin) / 2;

		s = delta == 0 ? 0 : delta / (1 - Math.abs(2 * l - 1));

		s *= 100;
		l *= 100;

		return (int)h + "°, " + (int)s + "%, " + (int)l + "%";
	}
}


