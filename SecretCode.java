import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  PermutationCode(Random rand) {
    this.rand = rand;
    this.code = this.initEncoder();
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> copy = new ArrayList<Character>(
        Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    return this.initEncoderHelper(new ArrayList<Character>(26), copy);
  }

  // initEncoder Helper
  ArrayList<Character> initEncoderHelper(ArrayList<Character> start, ArrayList<Character> result) {
    if (result.size() == 0) {
      return code;
    }
    else {
      int i = this.rand.nextInt(result.size());
      code.add(result.get(i));
      result.remove(i);
      return this.initEncoderHelper(code, result);
    }
  }

  // produce an encoded String from the given String
  String encode(String source) {
    return this.encodeHelper(source, "");
  }

  // Helper for encode method
  String encodeHelper(String source, String encoded) {
    if (source.equals("")) {
      return encoded;
    }
    else {
      Character first = source.charAt(0);
      String rest = source.substring(1);

      if (this.alphabet.contains(first)) {
        encoded = encoded.concat(this.code.get(this.alphabet.indexOf(first)).toString());
      }
      else {
        encoded = encoded.concat(first.toString());
      }
      return this.encodeHelper(rest, encoded);
    }
  }

  // produce a decoded String from the given String
  String decode(String code) {
    return this.decodeHelper(code, "");
  }

  // Helper method for decode
  String decodeHelper(String code, String message) {
    if (code.equals("")) {
      return message;
    }
    else {
      Character first = code.charAt(0);
      String rest = code.substring(1);

      if (this.alphabet.contains(first)) {
        message = message.concat(this.alphabet.get(this.code.indexOf(first)).toString());
      }
      else {
        message = message.concat(this.alphabet.get(this.code.indexOf(first)).toString());
      }
      return this.decodeHelper(rest, message);
    }
  }
}

class ExamplesEncoder {

  ArrayList<Character> abc = new ArrayList<Character>(
      Arrays.asList('l', 'q', 'p', 'n', 'v', 'a', 'z', 'w', 'v', 's', 'd', 'm', 'j', 'v', 's', 't',
          'b', 'e', 'c', 'g', 'r', 'y', 'f', 'h', 'g', 'i', 'o', 'k', 'q', 'u', 'x'));
  ArrayList<Character> codeEx1 = new ArrayList<Character>(
      Arrays.asList('b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'q', 't'));

  // abc
  // PermutationCode permutation1 = new PermutationCode();
  String encodedString = "qlp";
  String decodedString = "bac";

  // codeEx1
  String encodedString2 = "gbdf";
  String decodedString2 = "face";

  PermutationCode permutation2 = new PermutationCode(new Random(7));
  PermutationCode permutation3 = new PermutationCode(this.abc);
  PermutationCode permutation4 = new PermutationCode(this.codeEx1);

  // testing the encode method
  void testEncode(Tester t) {
    t.checkExpect(permutation3.encode(this.decodedString), this.encodedString);
    t.checkExpect(permutation4.encode(this.decodedString2), this.encodedString2);
    t.checkExpect(permutation3.encode(""), "");
    t.checkExpect(permutation3.encode(" "), " ");
  }

  // testing the decode method
  void testDecode(Tester t) {
    t.checkExpect(permutation3.decode(this.encodedString), this.decodedString);
    t.checkExpect(permutation4.decode(this.encodedString2), this.decodedString2);

    t.checkExpect(permutation3.decode("pnv"), "cde");
    t.checkExpect(permutation3.decode(""), "");
    t.checkExpect(permutation4.decode(""), "");
    t.checkExpect(permutation2.decode(""), "");
  }

  // testing InitEncoder method
  boolean testInitEncoder(Tester t) {
    return t.checkExpect(permutation3.initEncoder().size(), 57)
        && t.checkExpect(permutation4.initEncoder().size(), 36);
  }

  // testing Decode helper method
  boolean testDecodeHelper(Tester t) {
    return t.checkExpect(permutation3.decodeHelper(this.encodedString, "lol"), "lolbac")
        && t.checkExpect(permutation3.decodeHelper("", "abc"), "abc")
        && t.checkExpect(permutation3.decodeHelper("ha ha", "jk"), "jkha ha");
  }

  // testing the encodeHelper method
  boolean testEncodeHelper(Tester t) {
    return t.checkExpect(permutation3.encodeHelper("", "dog"), "dog")
        && t.checkExpect(permutation3.encodeHelper(encodedString, "bpc"), "bpcbmt")
        && t.checkExpect(permutation4.encodeHelper(encodedString2, "lol"), "lolhceg");
  }

  // testing the Permutation
  void testPermuation(Tester t) {

    t.checkExpect(permutation2.initEncoder(),
        new ArrayList<Character>(Arrays.asList('q', 'o', 'x', 'j', 'g', 'r', 'k', 'p', 'a', 'e',
            'i', 'w', 'u', 'n', 's', 'f', 'c', 'b', 'z', 'y', 't', 'v', 'm', 'h', 'l', 'd', 'd',
            'a', 'b', 'n', 'r', 'j', 'z', 't', 'o', 'v', 'i', 'l', 'u', 'k', 'm', 'x', 'g', 'c',
            'q', 'f', 'w', 'y', 'p', 'e', 's', 'h')));

  }
}
