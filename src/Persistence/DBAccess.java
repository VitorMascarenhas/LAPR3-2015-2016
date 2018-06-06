 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Factory.EngineFactory;
import Model.Junction;
import Model.Project;
import Model.Section;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Model.*;
import Model.Section.Typology;
import java.sql.CallableStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class DBAccess {

    private Connection conn;
    private String server;
    private String service;
    private String username;
    private String pass;

    public DBAccess(String server, String service, String username, String pass) {
        this.server = server;
        this.service = service;
        this.username = username;
        this.pass = pass;
        this.createDBConnection();
    }

    private void createDBConnection() {

        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + server + ":1521/" + service, username, pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + server + ":1521/" + service, username, pass);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (InstantiationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void closeConnection() {

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred closing connection." + e);
        }
    }

    private void makeCommit() throws SQLException {
        this.conn.commit();
    }

    public void saveProject(Project prj){

        try {
            String sql = "";
            CallableStatement cs = conn.prepareCall("{call SP_ADD_PROJECT(?,?,?)}");
            cs.setString(1, prj.getId());
            cs.setString(2, prj.getDescription());
            cs.registerOutParameter(3, java.sql.Types.INTEGER);
            cs.executeUpdate();
            int project_id = cs.getInt(3);
            cs.close();
            
            
            makeCommit();
            
            saveJunctions(prj.getJunctions(), project_id);
            saveRoads(prj.getSections(), project_id);
            saveDirections(prj.getSections());
            saveTypologies(prj.getSections());
            saveSections(prj, project_id);
            saveVehicles(prj, project_id);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveJunctions(ArrayList<Junction> junctions, int index_project) throws SQLException {
        
        String sql = "";
        for (Junction j : junctions) {
            try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_JUNCTION(?,?)}")) {
                stmt.setString(1, j.getId());
                stmt.setInt(2, index_project);
                stmt.execute();
                stmt.close();
            }
        }
        makeCommit();
    }

    private void saveSections(Project prj, int project_index) throws SQLException {

        String sql = "";
        for (Section s : prj.getSections()) {
            try (CallableStatement cs = conn.prepareCall("{call SP_ADD_SECTION(?,?,?,?,?,?,?,?,?,?)}")) {
                cs.setInt(1, project_index);
                cs.setString(2, s.getStartingNode().getId());
                cs.setString(3, s.getEndingNode().getId());
                cs.setString(4, s.getTypology().toString());
                cs.setString(5, s.getDirection().toString());
                cs.setString(6, s.getRoad());
                cs.setFloat(7, s.getToll());
                cs.setFloat(8, s.getWind().getAngle());
                cs.setFloat(9, s.getWind().getSpeed());
                cs.registerOutParameter(10, java.sql.Types.INTEGER);
                cs.executeUpdate();
                int section_index = cs.getInt(10);
                cs.close();
                for (Segment sg : s.getSegments()) {
                    saveSegment(s, section_index);
                }
            }
        }
        makeCommit();
    }

    private void saveSegment(Section section, int index) throws SQLException {

        String sql = "";
        try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_SEGMENT(?,?,?,?,?,?,?,?)}")) {
            for (Segment sg : section.getSegments()) {
                stmt.setInt(1, index);
                stmt.setFloat(2, sg.getSegmentIndex());
                stmt.setDouble(3, sg.getInitialHeight());
                stmt.setFloat(4, sg.getSlope());
                stmt.setFloat(5, sg.getLength());
                stmt.setInt(6, sg.getMaximumVelocity());
                stmt.setInt(7, sg.getMinimumVelocity());
                stmt.setInt(8, sg.getMaximumVehiclesNumber());
                stmt.execute();                
            }
            stmt.close();
        }
    }
    
    
    private void saveVehicles(Project prj, int project_index) throws SQLException {

        String sql = "";
        for (Vehicle v : prj.getVehicles()) {
            try (CallableStatement cs = conn.prepareCall("{call SP_ADD_VEHICLE(?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                cs.setInt(1, project_index);
                cs.setString(2, v.getName());
                cs.setString(3, v.getDescription());
                cs.setString(4, v.getType().toString());
                cs.setFloat(5, v.getLoad());
                cs.setFloat(6, v.getDrag());
                cs.setFloat(7, v.getRrc());
                cs.setFloat(8, v.getWheelSize());
                cs.setFloat(9, v.getMass());
                cs.setString(10, v.getEngine().getFuelType());
                cs.setFloat(11, v.getFrontalArea());
                cs.registerOutParameter(12, java.sql.Types.INTEGER);
                cs.executeUpdate();
                int vehicle_index = cs.getInt(12);
                saveEnergy(v.getEnergy(), vehicle_index);
                int velocity_index = 1;
                for(Velocity velo : v.getVelocities()){
                    saveVelocity(velo, vehicle_index, velocity_index);
                    velocity_index++;
                }
                cs.close();
            }
        }
        makeCommit();
    }
        
    private void saveVelocity(Velocity v, int index, int velocity_index) throws SQLException {

        String sql = "";
        try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_VELOCITY(?,?,?,?)}")) {
                stmt.setInt(1, index);
                stmt.setInt(2, velocity_index);
                stmt.setString(3, v.getTypology().toString());
                stmt.setFloat(4, v.getLimit());
                stmt.execute();
            stmt.close();
        }
    }
    
    private void saveEnergy(Energy e, int index) throws SQLException {

        String sql = "";
            try (CallableStatement cs = conn.prepareCall("{call SP_ADD_ENERGY(?,?,?,?,?,?)}")) {
                cs.setInt(1, index);
                cs.setInt(2, e.getMinRpm());
                cs.setInt(3, e.getMaxRpm());
                cs.setFloat(4, e.getFinalDriveRatio());
                cs.setFloat(5, e.getEnergyRegenerationRatio());
                cs.registerOutParameter(6, java.sql.Types.INTEGER);
                cs.executeUpdate();
                int energy_index = cs.getInt(6);
                for(Gear g : e.getGearList()){
                    saveGear(g, energy_index);
                }
                for(Throttle t : e.getThrottleList()){
                    saveThrottle(t, energy_index);
                }
                cs.close();
            }

        makeCommit();
    }
    
    private void saveGear(Gear g, int index) throws SQLException {

        String sql = "";
        try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_GEAR(?,?,?)}")) {
            stmt.setInt(1, index);
            stmt.setInt(2, g.getId());
            stmt.setFloat(3, g.getRatio());
            stmt.execute();
            stmt.close();
        }
    }
    
    private void saveThrottle(Throttle t, int index) throws SQLException {

        String sql = "";
            try (CallableStatement cs = conn.prepareCall("{call SP_ADD_THROTTLE(?,?,?)}")) {
                cs.setInt(1, index);
                cs.setInt(2, t.getId());
                cs.registerOutParameter(3, java.sql.Types.INTEGER);
                cs.executeUpdate();
                int throttle_index = cs.getInt(3);

                for(Regime r : t.getRegimes()){
                    saveRegime(r, throttle_index);
                }
                cs.close();
            }

        makeCommit();
    }

    private void saveRegime(Regime r, int index) throws SQLException {
        
        int torque;
        int rpm_low;
        int rpm_high;
        float sfc;
        
        String sql = "";
        try(PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_REGIME(?,?,?,?,?)}")) {
            stmt.setInt(1, index);
            stmt.setInt(2, r.getTorque());
            stmt.setInt(3, r.getRpm_low());
            stmt.setInt(4, r.getRpm_high());
            stmt.setFloat(5, r.getSfc());
            stmt.execute();
            stmt.close();
        }
        makeCommit();
    }  
    private void saveRoads(ArrayList<Section> sections, int prj_id) throws SQLException {

        ArrayList<String> roads = getRoads(sections);

        String sql = "";
        for (String str : roads) {
            try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_ROAD(?,?)}")) {
                stmt.setString(1, str);
                stmt.setInt(2, prj_id);
                stmt.execute();
                stmt.close();
            }
        }
        makeCommit();
    }

    private void saveDirections(ArrayList<Section> sections) throws SQLException {

        ArrayList<String> directions = getDirection(sections);

        String sql = "";
        for (String str : directions) {
            try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_DIRECTION(?)}")) {
                stmt.setString(1, str);
                stmt.execute();
                stmt.close();
            }
        }
        makeCommit();
    }

    private void saveTypologies(ArrayList<Section> sections) throws SQLException {

        ArrayList<String> typologies = getTypology(sections);

        String sql = "";
        for (String str : typologies) {
            try (PreparedStatement stmt = conn.prepareStatement("{call SP_ADD_TYPOLOGY(?)}")) {
                stmt.setString(1, str);
                stmt.execute();
                stmt.close();
            }
        }
        makeCommit();

    }

    private ArrayList<String> getRoads(ArrayList<Section> sections) {

        ArrayList<String> roads = new ArrayList<>();

        for (Section s : sections) {
            if (!roads.contains(s.getRoad())) {
                roads.add(s.getRoad());
            }
        }
        return roads;
    }

    private ArrayList<String> getTypology(ArrayList<Section> sections) {

        ArrayList<String> typology = new ArrayList<>();

        typology.add("REGULAR");
        typology.add("URBAN");
        typology.add("EXPRESS");
        typology.add("CONTROLLED");
        typology.add("HIGHWAY");

        return typology;
    }

    private ArrayList<String> getDirection(ArrayList<Section> sections) {

        ArrayList<String> direction = new ArrayList<>();

        direction.add("DIRECT");
        direction.add("REVERSE");
        direction.add("BIDIRECTIONAL");

        return direction;
    }
    
    public ArrayList<Project> getProjectsFromDB() {
        ArrayList<Project> projects = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String getProjects = "{call SP_GET_PROJECTS(?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getProjects);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(1);

            // loop it like normal
            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                String projectDescription = rs.getString("description");
                ArrayList<Junction> junctions = getJunctionsFromDB(projectId);
                ArrayList<Section> sections = getSectionsFromDB(projectId);
                ArrayList<Vehicle> vehicles = getVehiclesFromDB(projectId);
                Project p = new Project(projectName, projectDescription, junctions, sections);
                p.setVehicles(vehicles);
                projects.add(p);
            }
             callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return projects;
    }
    
    public Junction getJunctionFromDB(int junction_id) {
        Junction j = null;
        try {
            //getDBUSERCursor is a stored procedure
            String getJunctions = "{call SP_GET_JUNCTION(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getJunctions);
            callableStatement.setInt(1, junction_id);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();
            // get cursor and cast it to ResultSet
            String junctionName =  callableStatement.getString(2);
            callableStatement.close();
            j = new Junction(junctionName);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return j;
    }

    public ArrayList<Junction> getJunctionsFromDB(int project_id) {
        ArrayList<Junction> junctions = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String getJunctions = "{call SP_GET_JUNCTIONS(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getJunctions);
            callableStatement.setInt(1, project_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                String junctionName = rs.getString("junction_name");
                junctions.add(new Junction(junctionName));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return junctions;
    }
    
    public String getTypologyFromDB(int typology_id) {
        String t = "";
        try {
            //getDBUSERCursor is a stored procedure
            String getTypology = "{call SP_GET_TYPOLOGY(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getTypology);
            callableStatement.setInt(1, typology_id);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();
          
            // get cursor and cast it to ResultSet
            t =  callableStatement.getString(2);
             callableStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }
    
    public String getVehicleTypeFromDB(int vehicle_type) {
        String t = "";
        try {
            //getDBUSERCursor is a stored procedure
            String getTypology = "{call SP_GET_VEHICLETYPE(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getTypology);
            callableStatement.setInt(1, vehicle_type);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();
          
            // get cursor and cast it to ResultSet
            t =  callableStatement.getString(2);
             callableStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }
    
    
    public String getDirectionFromDB(int direction_id) {
        String d = "";
        try {
            //getDBUSERCursor is a stored procedure
            String getDirection = "{call SP_GET_DIRECTION(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getDirection);
            callableStatement.setInt(1, direction_id);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            d =  callableStatement.getString(2);
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
        public String getRoadFromDB(int road_id) {
        String r = "";
        try {
            //getDBUSERCursor is a stored procedure
            String getRoad = "{call SP_GET_ROAD(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(getRoad);
            callableStatement.setInt(1, road_id);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            r =  callableStatement.getString(2);
            callableStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    

    public ArrayList<Section> getSectionsFromDB(int project_id) {
        ArrayList<Section> sections = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_SECTIONS(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, project_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);
            // loop it like normal
            while (rs.next()) {
                int sectionId = rs.getInt("section_seq");
                int start_junction_id = rs.getInt("start_junction_id");
                int end_junction_id = rs.getInt("end_junction_id");
                int typology_id = rs.getInt("typology_id");
                int direction_id = rs.getInt("direction_id");
                int road_id = rs.getInt("road_id");
                float toll = rs.getFloat("toll");
                float wind_speed = rs.getFloat("wind_speed");
                float wind_angle = rs.getFloat("wind_angle");
                
                Junction jBegin = getJunctionFromDB(start_junction_id);
                Junction jEnd = getJunctionFromDB(end_junction_id);
                String typology = getTypologyFromDB(typology_id);
                System.out.println("tpolofy" + typology);
                String direction = getDirectionFromDB(direction_id);
                String road = getRoadFromDB(road_id);
                Wind w = new Wind(wind_speed, wind_angle);
                ArrayList<Segment> segments = getSegmentsFromDB(sectionId);
                ArrayList<Vehicle> vehicles = new ArrayList(); 
                sections.add(new Section(jBegin, jEnd, road, typology, direction, toll, w, segments, vehicles));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sections;
    }

    public ArrayList<Segment> getSegmentsFromDB(int section_id) {
        ArrayList<Segment> segments = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_SEGMENTS(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, section_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);
               
            // loop it like normal
            while (rs.next()) {
                int segment_id = rs.getInt("segment_id");
                int index_segment = rs.getInt("index_segment");
                float initial_height = rs.getFloat("initial_height");
                float slope = rs.getFloat("slope");
                float length = rs.getFloat("length");
                int min_value = rs.getInt("min_value");
                int max_value = rs.getInt("max_value");
                int max_vehicle = rs.getInt("max_vehicle");
                segments.add(new Segment(segment_id, initial_height, slope, length, min_value, max_vehicle, max_vehicle));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return segments;
    }



    public ArrayList<Vehicle> getVehiclesFromDB(int project_id) {
        ArrayList<Vehicle> vehicles = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_VEHICLES(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, project_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                int vehicle_id = rs.getInt("vehicle_id");
                String vehicle_name = rs.getString("vehicle_name");
                String description = rs.getString("description");
                int vehicle_type = rs.getInt("vehicle_type");
                float load = rs.getFloat("vload");
                float drag = rs.getFloat("drag");
                float rrc = rs.getFloat("rrc");
                float wheelSize = rs.getFloat("wheelSize");
                float mass = rs.getFloat("mass");
                String engine_type = rs.getString("engine_type");
                float frontal_area = rs.getFloat("frontal_area");
                ArrayList<Velocity> velocities = getVelocitiesFromDB(vehicle_id);
                Energy e = getEnergyFromDB(vehicle_id);
                Engine eng = null;
                EngineFactory ef = new EngineFactory();
                if(engine_type.equalsIgnoreCase("DIESEL") || engine_type.equalsIgnoreCase("GASOLINE") || engine_type.equalsIgnoreCase("HYDROGEN")){
                    eng = ef.getEngine("COMBUSTION", engine_type);
                }else{
                    eng = ef.getEngine("ELECTRIC", engine_type);
                }
                
                vehicles.add(new Vehicle(vehicle_name, description, getVehicleTypeFromDB(vehicle_type), load, drag, rrc, wheelSize, velocities, e, mass, eng,frontal_area));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehicles;
    }

    public ArrayList<Velocity> getVelocitiesFromDB(int vehicle_id) {
        ArrayList<Velocity> velocities = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_VELOCITIES(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, vehicle_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                int velocity_id = rs.getInt("velocity_id");
                int velocity_type = rs.getInt("velocity_typology");
                float limit = rs.getFloat("velocity_limit");
                velocities.add(new Velocity(getTypologyFromDB(velocity_type), limit));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return velocities;
    }

    public Energy getEnergyFromDB(int vehicle_id) {
        Energy e = null;
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_ENERGY(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, vehicle_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                int energy_id = rs.getInt("energy_id");
                int minRpm = rs.getInt("minRpm");
                int maxRpm = rs.getInt("maxRpm");
                float finalDriveRatio = rs.getFloat("finalDriveRatio");
                float energieRegenerationRatio = rs.getFloat("ENERGYREGENERATIONRATIO");
                ArrayList<Gear> gearList = getGearsFromDB(energy_id);
                ArrayList<Throttle> throttles = getThrottlesFromDB(energy_id);
                e = new Energy(minRpm, maxRpm, finalDriveRatio, gearList, throttles, energieRegenerationRatio);
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public ArrayList<Throttle> getThrottlesFromDB(int energy_id) {
        ArrayList<Throttle> throttles = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_THROTTLES(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, energy_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                int throttle_id = rs.getInt("throttle_id");
                int id = rs.getInt("id");
                ArrayList<Regime> regimes = getRegimesFromDB(throttle_id);
                throttles.add( new Throttle(id, regimes));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return throttles;
    }
//
    public ArrayList<Gear> getGearsFromDB(int energy_id) {
        ArrayList<Gear> gears = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_GEARS(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, energy_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                int gear_id = rs.getInt("gear_id");
                float ratio = rs.getFloat("ratio");
                gears.add( new Gear(gear_id, ratio));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gears;
    }

    public ArrayList<Regime> getRegimesFromDB(int throttle_id) {
        ArrayList<Regime> regimes = new ArrayList();
        try {
            //getDBUSERCursor is a stored procedure
            String call = "{call SP_GET_REGIMES(?,?)}";
            CallableStatement callableStatement = null;
            ResultSet rs = null;
            callableStatement = conn.prepareCall(call);
            callableStatement.setInt(1, throttle_id);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            // execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

            // get cursor and cast it to ResultSet
            rs = (ResultSet) callableStatement.getObject(2);

            // loop it like normal
            while (rs.next()) {
                int regime_id = rs.getInt("regime_id");
                int torque = rs.getInt("torque");
                int rpm_low = rs.getInt("rpm_low");
                int rpm_high = rs.getInt("rpm_high");
                float sfc = rs.getFloat("sfc");
                regimes.add(new Regime(torque, rpm_low, rpm_high, sfc));
            }
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return regimes;
    }

}
