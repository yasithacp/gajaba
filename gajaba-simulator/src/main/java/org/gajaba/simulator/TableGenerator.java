package org.gajaba.simulator;

import com.sun.enterprise.ee.cms.core.GMSFactory;
import com.sun.enterprise.ee.cms.core.GroupManagementService;
import org.gajaba.group.GMSSeparator;

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

    public String getHtml(org.gajaba.server.Server gajabaServer, GMSSeparator separator) {

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
            Object key =  pairs.getKey();
            html += "<tr><td>"+separator.getMemberTokenId(key)+"</td><td>"+separator.getKey(key)+"</td><td>"+pairs.getValue()+"</td></tr>";
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
