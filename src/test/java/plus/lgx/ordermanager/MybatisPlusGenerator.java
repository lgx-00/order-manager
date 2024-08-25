package plus.lgx.ordermanager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 类型：MybatisPlusGenerator
 *
 * @author lgx
 * @since 2024/8/24
 */
public class MybatisPlusGenerator {

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/order_manager", "root", "p@ssAdm7")
                // 全局配置
                .globalConfig((scanner, builder) -> {
                    builder.author("lgx").fileOverride();
                    builder.outputDir("/Users/lgx/Documents/Projects.localized/2024/java/order-manager/backend/src/main/java");
                    builder.disableOpenDir();
                })
                // 包配置
                .packageConfig((scanner, builder) -> {
                    builder.parent("plus.lgx.ordermanager");
                })
                // 策略配置
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                            .controllerBuilder().enableRestStyle().enableHyphenStyle().build();
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            .build();
                    //entity的策略配置
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .idType(IdType.AUTO)
                            .build();
                })
                .execute();
    }
}