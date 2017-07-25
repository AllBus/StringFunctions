package com.kos.wordcounter.core;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StringFunctions {
	/** @param text строка из которой берём число
	 * @param resultNoParse результат в случае ошибки при парсинге
	 * @return число которое представляет строка text, или resultNoParse при
	 *         ошибке чтения */
	public static int tryParse(String text, int resultNoParse) {
		try {
			return Integer.parseInt(text);
		}
		catch (Exception e) {
			return resultNoParse;
		}

	}

	public static long tryParseLong(String text, long resultNoParse) {
		try {
			return Long.parseLong(text);
		}
		catch (Exception e) {
			return resultNoParse;
		}

	}

	public static double tryParseDouble(String text, double resultNoParse) {
		try {
			return Double.parseDouble(text);
		}
		catch (Exception e) {
			return resultNoParse;
		}
	}


	public static String removeShortWord(String text, int minLength) {
		StringBuilder sb=new StringBuilder();
		for (String s : text.split(" ")) {
			if (s.length()>=minLength)
				sb.append(" ").append(s);
		}
		return sb.toString();
	}

	public static void removeOkonchanija(String[] sList) {
		for (int i=sList.length-1;i>=0;i--)
		{
			sList[i]=removeOkonchanija( sList[i]);
		}
	}
	//public static Set<String> slovarSet = new HashSet<>();
	/**
	 * Удалить окончание слова
	 *
	 * @param s слово
	 * @return слово без окончания или null если не менял
	 */
	public static String removeOkonchanija(final String s) {

		final int le=s.length();
		if (le >= 4) {
			switch (s.substring(le - 3)) {
				case "ыми":
				case "ого":
				case "ому":
				case "ими":
				case "его":
				case "ему":
				case "ями":
				case "ами":
					return s.substring(0, le - 3);

				default:

					switch (s.substring(le - 2)) {

						case "ый":
						case "ые":
						case "ых":
						case "ым":

						case "ов":
						case "ое":
						case "ом":
						case "ой":

						case "ая":
						case "ам":
						case "ах":

						case "ий":

						case "им":
						case "их":

						case "ее":
						case "ем":
						case "ей":

						case "ёй":
						case "ём":
						case "юю":
						case "ую":

						case "ью":
						case "яя":
						case "ях":
						case "ям":

							return s.substring(0, le - 2);

						case "ие": {
							if (prilagetelnoeIe(s)){
								return s.substring(0, le - 2);
							}else {
								return s.substring(0, le - 1);
							}
						}
						case "ок": {
							if (!iskluchenieOk(s)) {
								return s.substring(0, le - 2) + "к";
							} else
								return s;
						}
						case "ек": {
							char cc=s.charAt(le-3);
							if (cc=='ч' || cc=='ш' || cc=='ж' ) {
								if (!iskluchenieEk(s)) {
									return s.substring(0, le - 2) + "к";
								} else
									return s;
							}else
								return s;
						}
						default:
							switch (s.charAt(le - 1)) {
								case 'ь':
								case 'и':
								case 'а':
								case 'ы':
								case 'е':
								case 'о':
								case 'у':
								case 'я':
								case 'ю':
								case 'ё':
								case 'й':
									return s.substring(0, le - 1);
							}
					}

			}//end switch

		}//end if le>4
		return s;

	}

	private static boolean prilagetelnoeIe(String s) {
		//reqiure s.length>=4
		switch (s.charAt(s.length()-3)){
			case 'к':
			case 'ш':
			case 'щ':
			case 'г':
				return true;
			case 'н':
				if (s.charAt(s.length()-4)=='н')
					return true;
				return false;
			case 'ч':
				if ("различие".equals(s))
					return false;
				return true;
			default:
				return false;
		}




	}

	private static boolean iskluchenieOk(String s){
//reqiure s.length>=4
		switch (s){
			case "срок":
			case "сорок":
				return true;
			default:
				if (s.endsWith("восток") ||
					s.endsWith("переток"))
					return true;
				return false;
		}
	}

	private static boolean iskluchenieEk(String s){
		//reqiure s.length>=4
//		switch (s){
//			case "истек":
//			case "человек":
//			case "библиотек":
//				return true;
//			default:

				return false;
//		}
	}

//	public static void chisla(String word, TwoIntegerResult out)
//	{
//		//todo: сложные числовые записи (вида 1 098) неправильно обрабатываются
//		int value = 0;
//		int degree = 1;
//		boolean est = true;
//
//		switch (word)
//		{
//			case "один":
//			case "одного":
//			case "одной": 				value = 1; 				break;
//			case "два":
//			case "двух": 				value = 2; 				break;
//			case "три":
//			case "трех": 				value = 3; 				break;
//			case "четырех":				value = 4;				break;
//			case "пяти":				value = 5;				break;
//			case "шести":				value = 6;				break;
//			case "семи":				value = 7;				break;
//			case "восьми":				value = 8;				break;
//			case "девяти":				value = 9;				break;
//			case "десяти":				value = 10;				break;
//			case "двенадцати":			value = 12;				break;
//			case "пятнадцати":			value = 15;				break;
//			case "восемнадцати":		value = 18;				break;
//			case "двадцати":			value = 20;				break;
//			case "тридцати":			value = 30;				break;
//			case "сорока":				value = 40;				break;
//			case "пятидесяти":			value = 50;				break;
//			case "шестидесяти":			value = 60;				break;
//			case "семидесяти":			value = 70;				break;
//			case "восьмидесяти":		value = 80;				break;
//			case "девяноста":			value = 90;				break;
//			case "ста":					value = 100;			break;
//			case "двухсот":				value = 200;			break;
//			case "трехсот":				value = 300;			break;
//			case "четырехсот":
//			case "четыреста":			value = 400;			break;
//			case "пятисот":				value = 500;			break;
//			case "шестисот":			value = 600;			break;
//			case "семисот":				value = 700;			break;
//			case "восьмисот":			value = 800;			break;
//			case "девятисот":			value = 900;			break;
//			case "000":
//			case "тысячи":
//			case "тысяч":				degree = 1000;			break;
//			case "миллиона":
//			case "миллионов":			degree = 1000000;		break;
//
//			default:
//
//				for (int i=0;i<word.length() && est;i++){
//					char c=word.charAt(i);
//					if (c>='0' && c<='9'){
//						value=value*10+c-'0';
//					}
//					else
//						est=false;
//				}
//
//				break;
//		}
//		out.set(est, value, degree);
//	}
	public static String encodeSlash(String text) {
		return text.replace("┼","┼┴").replace("|","┼┬");
	}
	public static String decodeSlash(String text) {
		return text.replace("┼┬","|").replace("┼┴","┼");

	}

	public static String md5(String s) {
		String MD5 = "MD5";

		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			e.update(s.getBytes());
			byte[] messageDigest = e.digest();
			StringBuilder hexString = new StringBuilder();
			byte[] arr$ = messageDigest;
			int len$ = messageDigest.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				byte aMessageDigest = arr$[i$];

				String h;
				for(h = Integer.toHexString(255 & aMessageDigest); h.length() < 2; h = "0" + h) {
					;//empty body
				}

				hexString.append(h);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException var10) {
			return "";
		}
	}

	public static String generateHashFileName(String fileName) {
		String code=md5(fileName);
		if (code.length()>4){
			return code.substring(0,2)+"/"+code.substring(2,4)+"/"+code+"_"+new File(fileName).getName();
		}
		return null;
	}

	public static List<String> split(final String text, final char separator){

		List<String> ls = new ArrayList<>();
		int pos = 0;
		int pred = 0;
		while ((pos = text.indexOf(separator, pred)) >= 0) {
			if (pos > pred) {
				ls.add(text.substring(pred, pos));
			}
			pred = pos + 1;
		}
		if (pred < text.length()) {
			ls.add(text.substring(pred));
		}
		return ls;
	}

	public static String removeZnaks(final String text ) {
		final int le = text.length();

		int i = 0;
		while (i < le) {
			//final char c = text.charAt(i);
			if (Character.isLetterOrDigit(text.charAt(i))) {
				break;
			} else {
				i++;
			}
		}

		int k = le - 1;
		while (k > i) {
		//	final char c = text.charAt(k);
			if (Character.isLetterOrDigit(text.charAt(k))) {
				break;
			} else {
				k--;
			}
		}

		if (i <= k) {
			return text.substring(i, k + 1).toLowerCase();
		}
		else
			return "";

	}

	public static String[] splitRemoveZnakAndOkonchanija(String str){

		int end=str.length();
		final List<String> list = new ArrayList<>();

		int start=end;

		for (int i=str.length()-1;i>=0;i--){
			final char c=str.charAt(i);
			if (Character.isWhitespace(c)){
				if (end-start>=1){
					list.add(StringFunctions.removeOkonchanija(str.substring(start, end).toLowerCase(Locale.getDefault())));
				}
				end=i;
			}else
			if (Character.isLetterOrDigit(c))
			{
				start=i;

			}else{//Знаки препинания
				if (end-i==1){//Если знак в начале слова то его пропускаем
					end=i;
				}
			}
		}

		if (end-start>=1){
			list.add(StringFunctions.removeOkonchanija(str.substring(start, end).toLowerCase(Locale.getDefault())));
		}

		return list.toArray(new String[list.size()]);
	}

	public static String[] splitRemoveZnak(String str){

		int end=str.length();
		final List<String> list = new ArrayList<>();

		int start=end;

		for (int i=str.length()-1;i>=0;i--){
			final char c=str.charAt(i);
			if (Character.isWhitespace(c)){
				if (end-start>=1){
					list.add(str.substring(start, end).toLowerCase());
				}
				end=i;
			}else
			if (Character.isLetterOrDigit(c))
			{
				start=i;

			}else{//Знаки препинания
				if (end-i==1){//Если знак в начале слова то его пропускаем
					end=i;
				}
			}
		}

		if (end-start>=1){
			list.add(str.substring(start, end).toLowerCase());
		}

		return list.toArray(new String[list.size()]);
	}

	public static String drop(String[] arr, int dropCount, char separator){
		StringBuilder sb=new StringBuilder();
		final int le=arr.length;
		for (int i=dropCount;i<le;i++){
			if (i!=dropCount)
				sb.append(separator);
			sb.append(arr[i]);
		}
		return sb.toString();
	}
}
