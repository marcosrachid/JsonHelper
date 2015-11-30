package br.com.json.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.json.handler.utils.*;

public class Main {

	public static void main(String[] args) {

		String json = "{" + "\"glossary\": {" + "\"title\": \"example glossary\"," + "\"GlossDiv\": {"
				+ "\"title\": \"S\"," + "\"GlossList\": {" + "\"GlossEntry\": {" + "\"ID\": \"SGML\","
				+ "\"SortAs\": \"SGML\"," + "\"GlossTerm\": \"Standard Generalized Markup Language\","
				+ "\"Acronym\": \"SGML\"," + "\"Abbrev\": \"ISO 8879:1986\"," + "\"GlossDef\": {"
				+ "\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\","
				+ "\"GlossSeeAlso\": [\"GML\", \"XML\"]" + "}," + "\"GlossSee\": \"markup\"" + "}" + "}" + "}" + "}"
				+ "}";
		
		Map<String, Object> decoded = (Map<String, Object>) JsonHelper.jsonDecode(json);
		System.out.println(decoded.toString());
		
		List<Object> toEncode = new ArrayList<>();
		toEncode.add(new Integer(4));
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("Test", 1);
		map.put("Test2", decoded);
		toEncode.add(map);
		System.out.println(JsonHelper.jsonEncode(toEncode));

	}

}
