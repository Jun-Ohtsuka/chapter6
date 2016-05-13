package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.User;
import chapter6.service.UserService;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet{
	private static final long serialVersionUD = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		if(session.getAttribute("editUser") == null){
			User editUser = new User();
			session.setAttribute("editUser", editUser);
		}
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<String> messages = new ArrayList<>();
		HttpSession session = request.getSession();

		User user = getEditUser(request);
		session.setAttribute("editUser", user);

		if(isValid(request, messages) == true){
			try{
				new UserService().register(user);
				response.sendRedirect("./");
			}catch (RuntimeException e){
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("signup");
			}
		}else{
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}

	private User getEditUser(HttpServletRequest request) throws IOException, ServletException{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("editUser");

		user.setName(request.getParameter("name"));
		user.setAccount(request.getParameter("account"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setDescription(request.getParameter("description"));

		return user;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		if(StringUtils.isEmpty(account) == true){
			messages.add("アカウント名を入力してください");
		}
		if(StringUtils.isEmpty(password) == true){
			messages.add("パスワードを入力してください");
		}
		//TODO アカウントが既に利用されてないか、メールアドレスが既に登録されて内科などの確認も必要
//		Connection connection = null;
//		connection = getConnection();
//		UserDao userDao = new UserDao();
//		boolean check = userDao.checkAccount(connection, account);
//		System.out.println("check : " + check);//デバッグ用

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
