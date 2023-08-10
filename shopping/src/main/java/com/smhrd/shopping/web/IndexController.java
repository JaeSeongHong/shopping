package com.smhrd.shopping.web;

import java.io.FileReader;
import java.io.Reader;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.shopping.service.OrdersService;
import com.smhrd.shopping.service.ProductsService;
import com.smhrd.shopping.service.UsersService;
import com.smhrd.shopping.web.IndexController;
import com.smhrd.shopping.web.dto.OrderDetailsResDto;
import com.smhrd.shopping.web.dto.OrdersReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

	private final UsersService usersService;
	private final ProductsService productsService;
	private final OrdersService ordersService;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	private final String CLIENT_ID = "S1_fbc7fc6d26234c03a046d134c7d2cdb8";
	private final String SECRET_KEY = "93d08b95463d4465adb4d398437c5226";

	@GetMapping("/")
	public String index(ModelMap model) {
		usersService.save(); // 23.06.30 실제 회원정보를 구현하지 않기 때문에 회원 테이블에 어떤 정보도 없다면 user 계정 하나 임의 생성

		return "index";
	}

	@GetMapping("/product")
	public String test(Model model) {
		log.info("상품관리 이동...");
		model.addAttribute("products", productsService.findAll());
		return "product";
	}

	@GetMapping("/order")
	public String order(Model model) {
		log.info("주문하기 이동...");
		model.addAttribute("products", productsService.findAll());
		return "order";
	}

	@GetMapping("/orderList")
	public String orderList(Model model) {
		log.info("주문내역 이동...");
		model.addAttribute("orders", ordersService.findAllOrders());
		return "orderList";
	}

	@GetMapping("/apiserver")
	@ResponseBody
	public synchronized Map<String, Object> ApiServer(
			@RequestParam(value = "id", required = false, defaultValue = "1") Long id, HttpServletRequest request,
			ModelMap modelMap) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		List<OrderDetailsResDto> Test2 = ordersService.findByOrders(OrdersReqDto.builder().id(id).build());
		if (!Test2.isEmpty()) {
			result.put("result_msg", "Success");
			result.put("result_code", "00");
			result.put("data", Test2);
		} else {
			result.put("result_msg", "Fail");
			result.put("result_code", "01");
		}
		return result;

	}

	@GetMapping("/apiclient")
	@ResponseBody
	public Map<String, Object> ApiClient(HttpServletRequest request, ModelMap modelMap) throws Exception {
		JSONParser parser = new JSONParser();
		Reader reader = new FileReader("C:\\Test\\test.txt");
		JSONObject jsonObject = (JSONObject) parser.parse(reader);

		Map<String, Object> result = new HashMap<String, Object>();
		String result_msg = jsonObject.get("result_msg").toString();
		String result_code = jsonObject.get("result_code").toString();

		// System.err.println("result_code"+result_code);
		// 실습 : JSONArray 값 가져온 후 결과 값 확인

		JSONArray data = (JSONArray) jsonObject.get("data");
		System.out.println(data.size());

		for (int i = 0; i < data.size(); i++) {
			JSONObject one = (JSONObject) data.get(i);

			System.out.println("price : " + one.get("price").toString());
			System.out.println("list확인 : " + one.toString().toString());
		}

		if (result_msg.equals("SUCCESS")) {
			result.put("result_msg", "Success");
			result.put("result_code", "00");
			System.err.println();
		} else if (result_msg.equals("Fail")) {
			result.put("result_msg", "Fail");
			result.put("result_code", "01");
			System.err.println();
		} else {
			System.err.println();
		}

		return result;

	}

	@RequestMapping(value = "/cancel")
	public String cancelDemo() {
		return "/cancel";
	}

	@RequestMapping("/clientAuth")
	public String main(HttpServletRequest request, Model model) {
		String resultMsg = request.getParameter("resultMsg");
		String resultCode = request.getParameter("resultCode");
		model.addAttribute("resultMsg", resultMsg);

		if (resultCode.equalsIgnoreCase("0000")) {
			// 결제 성공 비즈니스 로직 구현
		} else {
			// 결제 실패 비즈니스 로직 구현
		}

		// request body 로그 확인
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			System.out.println(paramName + " : " + request.getParameter(paramName));
		}

		return "/response";
	}

	@RequestMapping("/cancelAuth")
	public String requestCancel(@RequestParam String tid, @RequestParam String amount, Model model) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization",
				"Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes()));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> AuthenticationMap = new HashMap<>();
		AuthenticationMap.put("amount", amount);
		AuthenticationMap.put("reason", "test");
		AuthenticationMap.put("orderId", UUID.randomUUID().toString());

		HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);

		ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
				"https://sandbox-api.nicepay.co.kr/v1/payments/" + tid + "/cancel", request, JsonNode.class);

		JsonNode responseNode = responseEntity.getBody();
		String resultCode = responseNode.get("resultCode").asText();
		model.addAttribute("resultMsg", responseNode.get("resultMsg").asText());

		System.out.println(responseNode.toPrettyString());

		if (resultCode.equalsIgnoreCase("0000")) {
			// 취소 성공 비즈니스 로직 구현
		} else {
			// 취소 실패 비즈니스 로직 구현
		}

		return "/response";
	}

}
