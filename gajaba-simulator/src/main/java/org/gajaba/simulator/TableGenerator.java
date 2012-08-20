package org.gajaba.simulator;

import com.sun.enterprise.ee.cms.core.GMSCacheable;
import com.sun.enterprise.ee.cms.core.GMSFactory;
import com.sun.enterprise.ee.cms.core.GroupManagementService;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yasitha
 * Date: 8/16/12
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TableGenerator {

    public String getHtml(org.gajaba.server.Server gajabaServer) {

        Map<String, String> map = gajabaServer.getDistributedcache();

        String html = "<table>\n" +
                "<tr>\n" +
                "<th>GMSMember</th>\n" +
                "<th>Key</th>\n" +
                "<th>Value</th>\n" +
                "</tr>\n";

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            GMSCacheable key = (GMSCacheable) pairs.getKey();
            html += "<tr><td>"+key.getMemberTokenId()+"</td><td>"+key.getKey()+"</td><td>"+pairs.getValue()+"</td></tr>";
            it.remove(); // avoids a ConcurrentModificationException
        }
        html += "</table>";
        return html;
//        return "<table>\n" +
//                "<tr>\n" +
//                "<td>row 1, cell 1</td>\n" +
//                "<td>row 1, cell 2</td>\n" +
//                "</tr>\n" +
//                "<tr>\n" +
//                "<td>row 2, cell 1</td>\n" +
//                "<td>row 2, cell 2</td>\n" +
//                "</tr>\n" +
//                "</table>";
    }

    private GroupManagementService initializeGMS(String serverName, String groupName) {
        return (GroupManagementService) GMSFactory.startGMSModule(serverName,
                groupName, GroupManagementService.MemberType.CORE, null);
    }

}
