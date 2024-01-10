/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/26/2023 - 6:29 PM
 */


package mockproject.backend.controller.user;

import mockproject.backend.domain.dto.PermissionForUpdate;
import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.entity.Role;
import mockproject.backend.domain.entity.RoleGroup;
import mockproject.backend.domain.entity.UserPermission;
import mockproject.backend.domain.entity.enums.EPermission;
import mockproject.backend.domain.entity.enums.ERole;
import mockproject.backend.repository.RoleGroupRepository;
import mockproject.backend.repository.RoleRepository;
import mockproject.backend.repository.UserPermissionRepository;
import mockproject.backend.utils.tools.PermissionExtraction;
import mockproject.backend.utils.tools.PermissionPackage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.EM00;
import static mockproject.backend.utils.constant.UserUrlAPI.USER_PERMISSION;
import static mockproject.backend.utils.constant.UserUrlAPI.USER_PERMISSION_EDIT;

@RestController
@RequestMapping
public class PermissionAPI {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserPermissionRepository userPermissionRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    //    get permission compact => Permission for update
    @GetMapping({USER_PERMISSION, USER_PERMISSION_EDIT})
    public ResponseEntity<Object> userPermission() {

        List<RoleGroup> superAdmin = roleGroupRepository.findAllRolePermission(ERole.SUPERADMIN);
        List<RoleGroup> admin = roleGroupRepository.findAllRolePermission(ERole.ADMIN);
        List<RoleGroup> trainer = roleGroupRepository.findAllRolePermission(ERole.TRAINER);

//        trainer with permission import syllabus -> set to active, avoid mistake when mapping
//        only set in case CRUD is active
        if (trainer.get(5).isActive() && trainer.get(6).isActive() &&
                trainer.get(7).isActive() && trainer.get(8).isActive()) {
            trainer.get(9).setActive(true);
        }
        List<Integer> preVar1 = new ArrayList<>();
        List<Integer> preVar2 = new ArrayList<>();
        List<Integer> preVar3 = new ArrayList<>();

//pre process data
        superAdmin.forEach(roleGroup -> preVar1.add(roleGroup.isActive() ? 1 : 0));
        admin.forEach(roleGroup -> preVar2.add(roleGroup.isActive() ? 1 : 0));
        trainer.forEach(roleGroup -> preVar3.add(roleGroup.isActive() ? 1 : 0));

        Integer[] var1 = preVar1.toArray(new Integer[20]);
        Integer[] var2 = preVar2.toArray(new Integer[20]);
        Integer[] var3 = preVar3.toArray(new Integer[20]);

//mapping code per matrix permission
        PermissionForUpdate permissionForUpdate = new PermissionForUpdate();
        permissionForUpdate.setRoleSuperAdmin(PermissionExtraction.preprocess(var1));
        permissionForUpdate.setRoleAdmin(PermissionExtraction.preprocess(var2));
        permissionForUpdate.setRoleTrainer(PermissionExtraction.preprocess(var3));

        return ResponseEntity.ok(new ResponseData(0, EM00, permissionForUpdate));
    }


    @PostMapping(USER_PERMISSION_EDIT)
    public ResponseEntity<Object> userPermission(
            @RequestBody PermissionForUpdate permissionForUpdate) {

        if (permissionForUpdate.getRoleSuperAdmin().length != 3 ||
                permissionForUpdate.getRoleAdmin().length != 3 ||
                permissionForUpdate.getRoleTrainer().length != 3)
            return ResponseEntity.ok(new ResponseData(-1, "bad request", null));
        Integer[][] matrixCode = new Integer[][]{permissionForUpdate.getRoleSuperAdmin(), permissionForUpdate.getRoleAdmin(), permissionForUpdate.getRoleTrainer()};

        for (int i = 0; i < matrixCode.length; i++) {
            if (matrixCode[i][i] == null) continue;//case admin update roll


            int roleId = i + 1;
            Role role = roleRepository.findById(Long.valueOf(roleId)).get();
            // deactivate all old permission of roles
            roleGroupRepository.updateDeactivePermissionByRole(role);
            //     get new mapping for permission role =>   set update new permission


            for (Integer[] ints : PermissionPackage.processPermission(roleId, matrixCode[i])) {
                Role role1 = roleRepository.findById(Long.valueOf(ints[0])).get();
                UserPermission permission = userPermissionRepository.findById((long) ints[1]).get();
//                do all, except trainer with permission import syllabus
                if (!(ints[0] == 3 && ints[1] == 10))
                    roleGroupRepository.updateIsActiveByRoleAndUserPermission(true, role1, permission);
            }
        }
        return ResponseEntity.ok(new ResponseData(0, EM00, null));
    }


    @GetMapping(USER_PERMISSION + "/detail")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','TRAINER')")
//    get list permission for each role
    public ResponseEntity<Object> userPermissionDetail() {
        ArrayList<ArrayList<EPermission>> result = new ArrayList<>();
        for (ERole value : ERole.values()) {
            Role role = roleRepository.findByRole(value);
            ArrayList<EPermission> listPermission = new ArrayList<>();
            role.getRoleGroupList().forEach(roleGroup -> {
                if (roleGroup.isActive()) listPermission.add(roleGroup.getUserPermission().getPermission());
            });
            result.add(listPermission);
        }
        return ResponseEntity.ok(new ResponseData(0, EM00, result));
    }
}
