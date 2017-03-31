/**
 * Created by Alex on 1/4/2017.
 */
class DepartmentFactory {
    static public Department getDepartment(String depType)
    {
        if(depType == null)
            return null;
        else if(depType.equalsIgnoreCase("bookdepartment"))
            return new BookDepartment();
        else if(depType.equalsIgnoreCase("musicdepartment"))
            return new MusicDepartment();
        else if(depType.equalsIgnoreCase("softwaredepartment"))
            return new SoftwareDepartment();
        else if(depType.equalsIgnoreCase("videodepartment"))
            return new VideoDepartment();
        return null;
    }
}
