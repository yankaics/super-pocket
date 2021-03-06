package com.superpocket.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.superpocket.kit.DataKit;
import com.superpocket.logic.ContentLogic;
import com.superpocket.logic.NetLogic;
import com.superpocket.logic.UserLogic;

/**
 * Servlet implementation class Save
 */
@WebServlet("/Save")
public class Save extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Save() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String data = DataKit.getJsonData(request.getReader());
		try {
			JSONObject json = new JSONObject(data);
			int uid = UserLogic.judgeUserStatus(request.getCookies());
			JSONObject retObj = new JSONObject();
			if (uid == 0) {
				retObj.put("success", "no");
			}
			else {
				JSONArray labels = json.getJSONArray("labels");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < labels.length(); ++i) {
					if (i > 0) sb = sb.append(",");
					sb = sb.append(labels.get(i));
				}
				if (sb.length() == 0) sb.append("未分类");
				int post_id = json.getInt("post_id");
				boolean ret = ContentLogic.save(uid, post_id, sb.toString());
						if (ret) retObj.put("success", "yes");
				else retObj.put("success", "no");
				NetLogic.writeJson(response, retObj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
