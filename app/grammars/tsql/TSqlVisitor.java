package grammars.tsql;

import grammars.Schema;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TSqlVisitor extends TSqlParserBaseVisitor<Schema> {
    @Override
    public Schema visitTsql_file(TSqlParser.Tsql_fileContext ctx) {
        return null;
    }

    @Override
    public Schema visitBatch(TSqlParser.BatchContext ctx) {
        return null;
    }

    @Override
    public Schema visitSql_clauses(TSqlParser.Sql_clausesContext ctx) {
        return null;
    }

    @Override
    public Schema visitSql_clause(TSqlParser.Sql_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitDml_clause(TSqlParser.Dml_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitDdl_clause(TSqlParser.Ddl_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitCfl_statement(TSqlParser.Cfl_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitBlock_statement(TSqlParser.Block_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitBreak_statement(TSqlParser.Break_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitContinue_statement(TSqlParser.Continue_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitGoto_statement(TSqlParser.Goto_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitReturn_statement(TSqlParser.Return_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitIf_statement(TSqlParser.If_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitThrow_statement(TSqlParser.Throw_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitThrow_error_number(TSqlParser.Throw_error_numberContext ctx) {
        return null;
    }

    @Override
    public Schema visitThrow_message(TSqlParser.Throw_messageContext ctx) {
        return null;
    }

    @Override
    public Schema visitThrow_state(TSqlParser.Throw_stateContext ctx) {
        return null;
    }

    @Override
    public Schema visitTry_catch_statement(TSqlParser.Try_catch_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitWaitfor_statement(TSqlParser.Waitfor_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitWhile_statement(TSqlParser.While_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitPrint_statement(TSqlParser.Print_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitRaiseerror_statement(TSqlParser.Raiseerror_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitEmpty_statement(TSqlParser.Empty_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitAnother_statement(TSqlParser.Another_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_application_role(TSqlParser.Alter_application_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_application_role(TSqlParser.Create_application_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_aggregate(TSqlParser.Drop_aggregateContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_application_role(TSqlParser.Drop_application_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly(TSqlParser.Alter_assemblyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_start(TSqlParser.Alter_assembly_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_clause(TSqlParser.Alter_assembly_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_from_clause(TSqlParser.Alter_assembly_from_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_from_clause_start(TSqlParser.Alter_assembly_from_clause_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_drop_clause(TSqlParser.Alter_assembly_drop_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_drop_multiple_files(TSqlParser.Alter_assembly_drop_multiple_filesContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_drop(TSqlParser.Alter_assembly_dropContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_add_clause(TSqlParser.Alter_assembly_add_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_asssembly_add_clause_start(TSqlParser.Alter_asssembly_add_clause_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_client_file_clause(TSqlParser.Alter_assembly_client_file_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_file_name(TSqlParser.Alter_assembly_file_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_file_bits(TSqlParser.Alter_assembly_file_bitsContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_as(TSqlParser.Alter_assembly_asContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_with_clause(TSqlParser.Alter_assembly_with_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_assembly_with(TSqlParser.Alter_assembly_withContext ctx) {
        return null;
    }

    @Override
    public Schema visitClient_assembly_specifier(TSqlParser.Client_assembly_specifierContext ctx) {
        return null;
    }

    @Override
    public Schema visitAssembly_option(TSqlParser.Assembly_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitNetwork_file_share(TSqlParser.Network_file_shareContext ctx) {
        return null;
    }

    @Override
    public Schema visitNetwork_computer(TSqlParser.Network_computerContext ctx) {
        return null;
    }

    @Override
    public Schema visitNetwork_file_start(TSqlParser.Network_file_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitFile_path(TSqlParser.File_pathContext ctx) {
        return null;
    }

    @Override
    public Schema visitFile_directory_path_separator(TSqlParser.File_directory_path_separatorContext ctx) {
        return null;
    }

    @Override
    public Schema visitLocal_file(TSqlParser.Local_fileContext ctx) {
        return null;
    }

    @Override
    public Schema visitLocal_drive(TSqlParser.Local_driveContext ctx) {
        return null;
    }

    @Override
    public Schema visitMultiple_local_files(TSqlParser.Multiple_local_filesContext ctx) {
        return null;
    }

    @Override
    public Schema visitMultiple_local_file_start(TSqlParser.Multiple_local_file_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_assembly(TSqlParser.Create_assemblyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_assembly(TSqlParser.Drop_assemblyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_asymmetric_key(TSqlParser.Alter_asymmetric_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_asymmetric_key_start(TSqlParser.Alter_asymmetric_key_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAsymmetric_key_option(TSqlParser.Asymmetric_key_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAsymmetric_key_option_start(TSqlParser.Asymmetric_key_option_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAsymmetric_key_password_change_option(TSqlParser.Asymmetric_key_password_change_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_asymmetric_key(TSqlParser.Create_asymmetric_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_asymmetric_key(TSqlParser.Drop_asymmetric_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_authorization(TSqlParser.Alter_authorizationContext ctx) {
        return null;
    }

    @Override
    public Schema visitAuthorization_grantee(TSqlParser.Authorization_granteeContext ctx) {
        return null;
    }

    @Override
    public Schema visitEntity_to(TSqlParser.Entity_toContext ctx) {
        return null;
    }

    @Override
    public Schema visitColon_colon(TSqlParser.Colon_colonContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_authorization_start(TSqlParser.Alter_authorization_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_authorization_for_sql_database(TSqlParser.Alter_authorization_for_sql_databaseContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_authorization_for_azure_dw(TSqlParser.Alter_authorization_for_azure_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_authorization_for_parallel_dw(TSqlParser.Alter_authorization_for_parallel_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitClass_type(TSqlParser.Class_typeContext ctx) {
        return null;
    }

    @Override
    public Schema visitClass_type_for_sql_database(TSqlParser.Class_type_for_sql_databaseContext ctx) {
        return null;
    }

    @Override
    public Schema visitClass_type_for_azure_dw(TSqlParser.Class_type_for_azure_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitClass_type_for_parallel_dw(TSqlParser.Class_type_for_parallel_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_availability_group(TSqlParser.Drop_availability_groupContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_availability_group(TSqlParser.Alter_availability_groupContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_availability_group_start(TSqlParser.Alter_availability_group_startContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_availability_group_options(TSqlParser.Alter_availability_group_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_broker_priority(TSqlParser.Create_or_alter_broker_priorityContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_broker_priority(TSqlParser.Drop_broker_priorityContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_certificate(TSqlParser.Alter_certificateContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_column_encryption_key(TSqlParser.Alter_column_encryption_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_column_encryption_key(TSqlParser.Create_column_encryption_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_certificate(TSqlParser.Drop_certificateContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_column_encryption_key(TSqlParser.Drop_column_encryption_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_column_master_key(TSqlParser.Drop_column_master_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_contract(TSqlParser.Drop_contractContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_credential(TSqlParser.Drop_credentialContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_cryptograhic_provider(TSqlParser.Drop_cryptograhic_providerContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_database(TSqlParser.Drop_databaseContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_database_audit_specification(TSqlParser.Drop_database_audit_specificationContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_database_scoped_credential(TSqlParser.Drop_database_scoped_credentialContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_default(TSqlParser.Drop_defaultContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_endpoint(TSqlParser.Drop_endpointContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_external_data_source(TSqlParser.Drop_external_data_sourceContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_external_file_format(TSqlParser.Drop_external_file_formatContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_external_library(TSqlParser.Drop_external_libraryContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_external_resource_pool(TSqlParser.Drop_external_resource_poolContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_external_table(TSqlParser.Drop_external_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_event_notifications(TSqlParser.Drop_event_notificationsContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_event_session(TSqlParser.Drop_event_sessionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_fulltext_catalog(TSqlParser.Drop_fulltext_catalogContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_fulltext_index(TSqlParser.Drop_fulltext_indexContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_fulltext_stoplist(TSqlParser.Drop_fulltext_stoplistContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_login(TSqlParser.Drop_loginContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_master_key(TSqlParser.Drop_master_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_message_type(TSqlParser.Drop_message_typeContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_partition_function(TSqlParser.Drop_partition_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_partition_scheme(TSqlParser.Drop_partition_schemeContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_queue(TSqlParser.Drop_queueContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_remote_service_binding(TSqlParser.Drop_remote_service_bindingContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_resource_pool(TSqlParser.Drop_resource_poolContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_db_role(TSqlParser.Drop_db_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_route(TSqlParser.Drop_routeContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_rule(TSqlParser.Drop_ruleContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_schema(TSqlParser.Drop_schemaContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_search_property_list(TSqlParser.Drop_search_property_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_security_policy(TSqlParser.Drop_security_policyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_sequence(TSqlParser.Drop_sequenceContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_server_audit(TSqlParser.Drop_server_auditContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_server_audit_specification(TSqlParser.Drop_server_audit_specificationContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_server_role(TSqlParser.Drop_server_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_service(TSqlParser.Drop_serviceContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_signature(TSqlParser.Drop_signatureContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_statistics_name_azure_dw_and_pdw(TSqlParser.Drop_statistics_name_azure_dw_and_pdwContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_symmetric_key(TSqlParser.Drop_symmetric_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_synonym(TSqlParser.Drop_synonymContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_user(TSqlParser.Drop_userContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_workload_group(TSqlParser.Drop_workload_groupContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_xml_schema_collection(TSqlParser.Drop_xml_schema_collectionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDisable_trigger(TSqlParser.Disable_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitEnable_trigger(TSqlParser.Enable_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitTruncate_table(TSqlParser.Truncate_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_column_master_key(TSqlParser.Create_column_master_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_credential(TSqlParser.Alter_credentialContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_credential(TSqlParser.Create_credentialContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_cryptographic_provider(TSqlParser.Alter_cryptographic_providerContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_cryptographic_provider(TSqlParser.Create_cryptographic_providerContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_event_notification(TSqlParser.Create_event_notificationContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_event_session(TSqlParser.Create_or_alter_event_sessionContext ctx) {
        return null;
    }

    @Override
    public Schema visitEvent_session_predicate_expression(TSqlParser.Event_session_predicate_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitEvent_session_predicate_factor(TSqlParser.Event_session_predicate_factorContext ctx) {
        return null;
    }

    @Override
    public Schema visitEvent_session_predicate_leaf(TSqlParser.Event_session_predicate_leafContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_external_data_source(TSqlParser.Alter_external_data_sourceContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_external_library(TSqlParser.Alter_external_libraryContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_external_library(TSqlParser.Create_external_libraryContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_external_resource_pool(TSqlParser.Alter_external_resource_poolContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_external_resource_pool(TSqlParser.Create_external_resource_poolContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_fulltext_catalog(TSqlParser.Alter_fulltext_catalogContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_fulltext_catalog(TSqlParser.Create_fulltext_catalogContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_fulltext_stoplist(TSqlParser.Alter_fulltext_stoplistContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_fulltext_stoplist(TSqlParser.Create_fulltext_stoplistContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_login_sql_server(TSqlParser.Alter_login_sql_serverContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_login_sql_server(TSqlParser.Create_login_sql_serverContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_login_azure_sql(TSqlParser.Alter_login_azure_sqlContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_login_azure_sql(TSqlParser.Create_login_azure_sqlContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_login_azure_sql_dw_and_pdw(TSqlParser.Alter_login_azure_sql_dw_and_pdwContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_login_pdw(TSqlParser.Create_login_pdwContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_master_key_sql_server(TSqlParser.Alter_master_key_sql_serverContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_master_key_sql_server(TSqlParser.Create_master_key_sql_serverContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_master_key_azure_sql(TSqlParser.Alter_master_key_azure_sqlContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_master_key_azure_sql(TSqlParser.Create_master_key_azure_sqlContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_message_type(TSqlParser.Alter_message_typeContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_partition_function(TSqlParser.Alter_partition_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_partition_scheme(TSqlParser.Alter_partition_schemeContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_remote_service_binding(TSqlParser.Alter_remote_service_bindingContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_remote_service_binding(TSqlParser.Create_remote_service_bindingContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_resource_pool(TSqlParser.Create_resource_poolContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_resource_governor(TSqlParser.Alter_resource_governorContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_db_role(TSqlParser.Alter_db_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_db_role(TSqlParser.Create_db_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_route(TSqlParser.Create_routeContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_rule(TSqlParser.Create_ruleContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_schema_sql(TSqlParser.Alter_schema_sqlContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_schema(TSqlParser.Create_schemaContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_schema_azure_sql_dw_and_pdw(TSqlParser.Create_schema_azure_sql_dw_and_pdwContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_schema_azure_sql_dw_and_pdw(TSqlParser.Alter_schema_azure_sql_dw_and_pdwContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_search_property_list(TSqlParser.Create_search_property_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_security_policy(TSqlParser.Create_security_policyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_sequence(TSqlParser.Alter_sequenceContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_sequence(TSqlParser.Create_sequenceContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_server_audit(TSqlParser.Alter_server_auditContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_server_audit(TSqlParser.Create_server_auditContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_server_audit_specification(TSqlParser.Alter_server_audit_specificationContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_server_audit_specification(TSqlParser.Create_server_audit_specificationContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_server_configuration(TSqlParser.Alter_server_configurationContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_server_role(TSqlParser.Alter_server_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_server_role(TSqlParser.Create_server_roleContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_server_role_pdw(TSqlParser.Alter_server_role_pdwContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_service(TSqlParser.Alter_serviceContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_service(TSqlParser.Create_serviceContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_service_master_key(TSqlParser.Alter_service_master_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_symmetric_key(TSqlParser.Alter_symmetric_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_symmetric_key(TSqlParser.Create_symmetric_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_synonym(TSqlParser.Create_synonymContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_user(TSqlParser.Alter_userContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_user(TSqlParser.Create_userContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_user_azure_sql_dw(TSqlParser.Create_user_azure_sql_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_user_azure_sql(TSqlParser.Alter_user_azure_sqlContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_workload_group(TSqlParser.Alter_workload_groupContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_workload_group(TSqlParser.Create_workload_groupContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_xml_schema_collection(TSqlParser.Create_xml_schema_collectionContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_queue(TSqlParser.Create_queueContext ctx) {
        return null;
    }

    @Override
    public Schema visitQueue_settings(TSqlParser.Queue_settingsContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_queue(TSqlParser.Alter_queueContext ctx) {
        return null;
    }

    @Override
    public Schema visitQueue_action(TSqlParser.Queue_actionContext ctx) {
        return null;
    }

    @Override
    public Schema visitQueue_rebuild_options(TSqlParser.Queue_rebuild_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_contract(TSqlParser.Create_contractContext ctx) {
        return null;
    }

    @Override
    public Schema visitConversation_statement(TSqlParser.Conversation_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitMessage_statement(TSqlParser.Message_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitMerge_statement(TSqlParser.Merge_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitMerge_matched(TSqlParser.Merge_matchedContext ctx) {
        return null;
    }

    @Override
    public Schema visitMerge_not_matched(TSqlParser.Merge_not_matchedContext ctx) {
        return null;
    }

    @Override
    public Schema visitDelete_statement(TSqlParser.Delete_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitDelete_statement_from(TSqlParser.Delete_statement_fromContext ctx) {
        return null;
    }

    @Override
    public Schema visitInsert_statement(TSqlParser.Insert_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitInsert_statement_value(TSqlParser.Insert_statement_valueContext ctx) {
        return null;
    }

    @Override
    public Schema visitReceive_statement(TSqlParser.Receive_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitSelect_statement(TSqlParser.Select_statementContext ctx) {
//        System.out.println(ctx.query_expression().);
        return null;
    }

    @Override
    public Schema visitTime(TSqlParser.TimeContext ctx) {
        return null;
    }

    @Override
    public Schema visitUpdate_statement(TSqlParser.Update_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitOutput_clause(TSqlParser.Output_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitOutput_dml_list_elem(TSqlParser.Output_dml_list_elemContext ctx) {
        return null;
    }

    @Override
    public Schema visitOutput_column_name(TSqlParser.Output_column_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_database(TSqlParser.Create_databaseContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_index(TSqlParser.Create_indexContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_procedure(TSqlParser.Create_or_alter_procedureContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_trigger(TSqlParser.Create_or_alter_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_dml_trigger(TSqlParser.Create_or_alter_dml_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitDml_trigger_option(TSqlParser.Dml_trigger_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDml_trigger_operation(TSqlParser.Dml_trigger_operationContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_ddl_trigger(TSqlParser.Create_or_alter_ddl_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitDdl_trigger_operation(TSqlParser.Ddl_trigger_operationContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_or_alter_function(TSqlParser.Create_or_alter_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitFunc_body_returns_select(TSqlParser.Func_body_returns_selectContext ctx) {
        return null;
    }

    @Override
    public Schema visitFunc_body_returns_table(TSqlParser.Func_body_returns_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitFunc_body_returns_scalar(TSqlParser.Func_body_returns_scalarContext ctx) {
        return null;
    }

    @Override
    public Schema visitProcedure_param(TSqlParser.Procedure_paramContext ctx) {
        return null;
    }

    @Override
    public Schema visitProcedure_option(TSqlParser.Procedure_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitFunction_option(TSqlParser.Function_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_statistics(TSqlParser.Create_statisticsContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_table(TSqlParser.Create_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_options(TSqlParser.Table_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_view(TSqlParser.Create_viewContext ctx) {
        return null;
    }

    @Override
    public Schema visitView_attribute(TSqlParser.View_attributeContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_table(TSqlParser.Alter_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_database(TSqlParser.Alter_databaseContext ctx) {
        return null;
    }

    @Override
    public Schema visitDatabase_optionspec(TSqlParser.Database_optionspecContext ctx) {
        return null;
    }

    @Override
    public Schema visitAuto_option(TSqlParser.Auto_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitChange_tracking_option(TSqlParser.Change_tracking_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitChange_tracking_option_list(TSqlParser.Change_tracking_option_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitContainment_option(TSqlParser.Containment_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitCursor_option(TSqlParser.Cursor_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlter_endpoint(TSqlParser.Alter_endpointContext ctx) {
        return null;
    }

    @Override
    public Schema visitDatabase_mirroring_option(TSqlParser.Database_mirroring_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitMirroring_set_option(TSqlParser.Mirroring_set_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitMirroring_partner(TSqlParser.Mirroring_partnerContext ctx) {
        return null;
    }

    @Override
    public Schema visitMirroring_witness(TSqlParser.Mirroring_witnessContext ctx) {
        return null;
    }

    @Override
    public Schema visitWitness_partner_equal(TSqlParser.Witness_partner_equalContext ctx) {
        return null;
    }

    @Override
    public Schema visitPartner_option(TSqlParser.Partner_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitWitness_option(TSqlParser.Witness_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitWitness_server(TSqlParser.Witness_serverContext ctx) {
        return null;
    }

    @Override
    public Schema visitPartner_server(TSqlParser.Partner_serverContext ctx) {
        return null;
    }

    @Override
    public Schema visitMirroring_host_port_seperator(TSqlParser.Mirroring_host_port_seperatorContext ctx) {
        return null;
    }

    @Override
    public Schema visitPartner_server_tcp_prefix(TSqlParser.Partner_server_tcp_prefixContext ctx) {
        return null;
    }

    @Override
    public Schema visitPort_number(TSqlParser.Port_numberContext ctx) {
        return null;
    }

    @Override
    public Schema visitHost(TSqlParser.HostContext ctx) {
        return null;
    }

    @Override
    public Schema visitDate_correlation_optimization_option(TSqlParser.Date_correlation_optimization_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDb_encryption_option(TSqlParser.Db_encryption_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDb_state_option(TSqlParser.Db_state_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDb_update_option(TSqlParser.Db_update_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDb_user_access_option(TSqlParser.Db_user_access_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDelayed_durability_option(TSqlParser.Delayed_durability_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitExternal_access_option(TSqlParser.External_access_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitHadr_options(TSqlParser.Hadr_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitMixed_page_allocation_option(TSqlParser.Mixed_page_allocation_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitParameterization_option(TSqlParser.Parameterization_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitRecovery_option(TSqlParser.Recovery_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitService_broker_option(TSqlParser.Service_broker_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSnapshot_option(TSqlParser.Snapshot_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSql_option(TSqlParser.Sql_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitTarget_recovery_time_option(TSqlParser.Target_recovery_time_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitTermination(TSqlParser.TerminationContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_index(TSqlParser.Drop_indexContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_relational_or_xml_or_spatial_index(TSqlParser.Drop_relational_or_xml_or_spatial_indexContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_backward_compatible_index(TSqlParser.Drop_backward_compatible_indexContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_procedure(TSqlParser.Drop_procedureContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_trigger(TSqlParser.Drop_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_dml_trigger(TSqlParser.Drop_dml_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_ddl_trigger(TSqlParser.Drop_ddl_triggerContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_function(TSqlParser.Drop_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_statistics(TSqlParser.Drop_statisticsContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_table(TSqlParser.Drop_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_view(TSqlParser.Drop_viewContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_type(TSqlParser.Create_typeContext ctx) {
        return null;
    }

    @Override
    public Schema visitDrop_type(TSqlParser.Drop_typeContext ctx) {
        return null;
    }

    @Override
    public Schema visitRowset_function_limited(TSqlParser.Rowset_function_limitedContext ctx) {
        return null;
    }

    @Override
    public Schema visitOpenquery(TSqlParser.OpenqueryContext ctx) {
        return null;
    }

    @Override
    public Schema visitOpendatasource(TSqlParser.OpendatasourceContext ctx) {
        return null;
    }

    @Override
    public Schema visitDeclare_statement(TSqlParser.Declare_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitCursor_statement(TSqlParser.Cursor_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitExecute_statement(TSqlParser.Execute_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitExecute_statement_arg(TSqlParser.Execute_statement_argContext ctx) {
        return null;
    }

    @Override
    public Schema visitExecute_var_string(TSqlParser.Execute_var_stringContext ctx) {
        return null;
    }

    @Override
    public Schema visitSecurity_statement(TSqlParser.Security_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_certificate(TSqlParser.Create_certificateContext ctx) {
        return null;
    }

    @Override
    public Schema visitExisting_keys(TSqlParser.Existing_keysContext ctx) {
        return null;
    }

    @Override
    public Schema visitPrivate_key_options(TSqlParser.Private_key_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitGenerate_new_keys(TSqlParser.Generate_new_keysContext ctx) {
        return null;
    }

    @Override
    public Schema visitDate_options(TSqlParser.Date_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitOpen_key(TSqlParser.Open_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitClose_key(TSqlParser.Close_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_key(TSqlParser.Create_keyContext ctx) {
        return null;
    }

    @Override
    public Schema visitKey_options(TSqlParser.Key_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitAlgorithm(TSqlParser.AlgorithmContext ctx) {
        return null;
    }

    @Override
    public Schema visitEncryption_mechanism(TSqlParser.Encryption_mechanismContext ctx) {
        return null;
    }

    @Override
    public Schema visitDecryption_mechanism(TSqlParser.Decryption_mechanismContext ctx) {
        return null;
    }

    @Override
    public Schema visitGrant_permission(TSqlParser.Grant_permissionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSet_statement(TSqlParser.Set_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitTransaction_statement(TSqlParser.Transaction_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitGo_statement(TSqlParser.Go_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitUse_statement(TSqlParser.Use_statementContext ctx) {
        return null;
    }

    @Override
    public Schema visitDbcc_clause(TSqlParser.Dbcc_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitDbcc_options(TSqlParser.Dbcc_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitExecute_clause(TSqlParser.Execute_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitDeclare_local(TSqlParser.Declare_localContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_type_definition(TSqlParser.Table_type_definitionContext ctx) {
        return null;
    }

    @Override
    public Schema visitXml_type_definition(TSqlParser.Xml_type_definitionContext ctx) {
        return null;
    }

    @Override
    public Schema visitXml_schema_collection(TSqlParser.Xml_schema_collectionContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_def_table_constraints(TSqlParser.Column_def_table_constraintsContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_def_table_constraint(TSqlParser.Column_def_table_constraintContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_definition(TSqlParser.Column_definitionContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_constraint(TSqlParser.Column_constraintContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_constraint(TSqlParser.Table_constraintContext ctx) {
        return null;
    }

    @Override
    public Schema visitOn_delete(TSqlParser.On_deleteContext ctx) {
        return null;
    }

    @Override
    public Schema visitOn_update(TSqlParser.On_updateContext ctx) {
        return null;
    }

    @Override
    public Schema visitIndex_options(TSqlParser.Index_optionsContext ctx) {
        return null;
    }

    @Override
    public Schema visitIndex_option(TSqlParser.Index_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDeclare_cursor(TSqlParser.Declare_cursorContext ctx) {
        return null;
    }

    @Override
    public Schema visitDeclare_set_cursor_common(TSqlParser.Declare_set_cursor_commonContext ctx) {
        return null;
    }

    @Override
    public Schema visitDeclare_set_cursor_common_partial(TSqlParser.Declare_set_cursor_common_partialContext ctx) {
        return null;
    }

    @Override
    public Schema visitFetch_cursor(TSqlParser.Fetch_cursorContext ctx) {
        return null;
    }

    @Override
    public Schema visitSet_special(TSqlParser.Set_specialContext ctx) {
        return null;
    }

    @Override
    public Schema visitConstant_LOCAL_ID(TSqlParser.Constant_LOCAL_IDContext ctx) {
        return null;
    }

    @Override
    public Schema visitExpression(TSqlParser.ExpressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitPrimitive_expression(TSqlParser.Primitive_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitCase_expression(TSqlParser.Case_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitUnary_operator_expression(TSqlParser.Unary_operator_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitBracket_expression(TSqlParser.Bracket_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitConstant_expression(TSqlParser.Constant_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSubquery(TSqlParser.SubqueryContext ctx) {
        return null;
    }

    @Override
    public Schema visitWith_expression(TSqlParser.With_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitCommon_table_expression(TSqlParser.Common_table_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitUpdate_elem(TSqlParser.Update_elemContext ctx) {
        return null;
    }

    @Override
    public Schema visitSearch_condition_list(TSqlParser.Search_condition_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitSearch_condition(TSqlParser.Search_conditionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSearch_condition_and(TSqlParser.Search_condition_andContext ctx) {
        return null;
    }

    @Override
    public Schema visitSearch_condition_not(TSqlParser.Search_condition_notContext ctx) {
        return null;
    }

    @Override
    public Schema visitPredicate(TSqlParser.PredicateContext ctx) {
        return null;
    }

    @Override
    public Schema visitQuery_expression(TSqlParser.Query_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSql_union(TSqlParser.Sql_unionContext ctx) {
        return null;
    }

    @Override
    public Schema visitQuery_specification(TSqlParser.Query_specificationContext ctx) {
        return null;
    }

    @Override
    public Schema visitTop_clause(TSqlParser.Top_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitTop_percent(TSqlParser.Top_percentContext ctx) {
        return null;
    }

    @Override
    public Schema visitTop_count(TSqlParser.Top_countContext ctx) {
        return null;
    }

    @Override
    public Schema visitOrder_by_clause(TSqlParser.Order_by_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitFor_clause(TSqlParser.For_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitXml_common_directives(TSqlParser.Xml_common_directivesContext ctx) {
        return null;
    }

    @Override
    public Schema visitOrder_by_expression(TSqlParser.Order_by_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitGroup_by_item(TSqlParser.Group_by_itemContext ctx) {
        return null;
    }

    @Override
    public Schema visitOption_clause(TSqlParser.Option_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitOption(TSqlParser.OptionContext ctx) {
        return null;
    }

    @Override
    public Schema visitOptimize_for_arg(TSqlParser.Optimize_for_argContext ctx) {
        return null;
    }

    @Override
    public Schema visitSelect_list(TSqlParser.Select_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitUdt_method_arguments(TSqlParser.Udt_method_argumentsContext ctx) {
        return null;
    }

    @Override
    public Schema visitAsterisk(TSqlParser.AsteriskContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_elem(TSqlParser.Column_elemContext ctx) {
        return null;
    }

    @Override
    public Schema visitUdt_elem(TSqlParser.Udt_elemContext ctx) {
        return null;
    }

    @Override
    public Schema visitExpression_elem(TSqlParser.Expression_elemContext ctx) {
        return null;
    }

    @Override
    public Schema visitSelect_list_elem(TSqlParser.Select_list_elemContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_sources(TSqlParser.Table_sourcesContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_source(TSqlParser.Table_sourceContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_source_item_joined(TSqlParser.Table_source_item_joinedContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_source_item(TSqlParser.Table_source_itemContext ctx) {
        return null;
    }

    @Override
    public Schema visitOpen_xml(TSqlParser.Open_xmlContext ctx) {
        return null;
    }

    @Override
    public Schema visitSchema_declaration(TSqlParser.Schema_declarationContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_declaration(TSqlParser.Column_declarationContext ctx) {
        return null;
    }

    @Override
    public Schema visitChange_table(TSqlParser.Change_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitJoin_part(TSqlParser.Join_partContext ctx) {
        return null;
    }

    @Override
    public Schema visitPivot_clause(TSqlParser.Pivot_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitUnpivot_clause(TSqlParser.Unpivot_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitFull_column_name_list(TSqlParser.Full_column_name_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_name_with_hint(TSqlParser.Table_name_with_hintContext ctx) {
        return null;
    }

    @Override
    public Schema visitRowset_function(TSqlParser.Rowset_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitBulk_option(TSqlParser.Bulk_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDerived_table(TSqlParser.Derived_tableContext ctx) {
        return null;
    }

    @Override
    public Schema visitRANKING_WINDOWED_FUNC(TSqlParser.RANKING_WINDOWED_FUNCContext ctx) {
        return null;
    }

    @Override
    public Schema visitAGGREGATE_WINDOWED_FUNC(TSqlParser.AGGREGATE_WINDOWED_FUNCContext ctx) {
        return null;
    }

    @Override
    public Schema visitANALYTIC_WINDOWED_FUNC(TSqlParser.ANALYTIC_WINDOWED_FUNCContext ctx) {
        return null;
    }

    @Override
    public Schema visitSCALAR_FUNCTION(TSqlParser.SCALAR_FUNCTIONContext ctx) {
        return null;
    }

    @Override
    public Schema visitBINARY_CHECKSUM(TSqlParser.BINARY_CHECKSUMContext ctx) {
        return null;
    }

    @Override
    public Schema visitCAST(TSqlParser.CASTContext ctx) {
        return null;
    }

    @Override
    public Schema visitCONVERT(TSqlParser.CONVERTContext ctx) {
        return null;
    }

    @Override
    public Schema visitCHECKSUM(TSqlParser.CHECKSUMContext ctx) {
        return null;
    }

    @Override
    public Schema visitCOALESCE(TSqlParser.COALESCEContext ctx) {
        return null;
    }

    @Override
    public Schema visitCURRENT_TIMESTAMP(TSqlParser.CURRENT_TIMESTAMPContext ctx) {
        return null;
    }

    @Override
    public Schema visitCURRENT_USER(TSqlParser.CURRENT_USERContext ctx) {
        return null;
    }

    @Override
    public Schema visitDATEADD(TSqlParser.DATEADDContext ctx) {
        return null;
    }

    @Override
    public Schema visitDATEDIFF(TSqlParser.DATEDIFFContext ctx) {
        return null;
    }

    @Override
    public Schema visitDATENAME(TSqlParser.DATENAMEContext ctx) {
        return null;
    }

    @Override
    public Schema visitDATEPART(TSqlParser.DATEPARTContext ctx) {
        return null;
    }

    @Override
    public Schema visitGETDATE(TSqlParser.GETDATEContext ctx) {
        return null;
    }

    @Override
    public Schema visitGETUTCDATE(TSqlParser.GETUTCDATEContext ctx) {
        return null;
    }

    @Override
    public Schema visitIDENTITY(TSqlParser.IDENTITYContext ctx) {
        return null;
    }

    @Override
    public Schema visitMIN_ACTIVE_ROWVERSION(TSqlParser.MIN_ACTIVE_ROWVERSIONContext ctx) {
        return null;
    }

    @Override
    public Schema visitNULLIF(TSqlParser.NULLIFContext ctx) {
        return null;
    }

    @Override
    public Schema visitSTUFF(TSqlParser.STUFFContext ctx) {
        return null;
    }

    @Override
    public Schema visitSESSION_USER(TSqlParser.SESSION_USERContext ctx) {
        return null;
    }

    @Override
    public Schema visitSYSTEM_USER(TSqlParser.SYSTEM_USERContext ctx) {
        return null;
    }

    @Override
    public Schema visitISNULL(TSqlParser.ISNULLContext ctx) {
        return null;
    }

    @Override
    public Schema visitXML_DATA_TYPE_FUNC(TSqlParser.XML_DATA_TYPE_FUNCContext ctx) {
        return null;
    }

    @Override
    public Schema visitXml_data_type_methods(TSqlParser.Xml_data_type_methodsContext ctx) {
        return null;
    }

    @Override
    public Schema visitValue_method(TSqlParser.Value_methodContext ctx) {
        return null;
    }

    @Override
    public Schema visitQuery_method(TSqlParser.Query_methodContext ctx) {
        return null;
    }

    @Override
    public Schema visitExist_method(TSqlParser.Exist_methodContext ctx) {
        return null;
    }

    @Override
    public Schema visitModify_method(TSqlParser.Modify_methodContext ctx) {
        return null;
    }

    @Override
    public Schema visitNodes_method(TSqlParser.Nodes_methodContext ctx) {
        return null;
    }

    @Override
    public Schema visitSwitch_section(TSqlParser.Switch_sectionContext ctx) {
        return null;
    }

    @Override
    public Schema visitSwitch_search_condition_section(TSqlParser.Switch_search_condition_sectionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAs_column_alias(TSqlParser.As_column_aliasContext ctx) {
        return null;
    }

    @Override
    public Schema visitAs_table_alias(TSqlParser.As_table_aliasContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_alias(TSqlParser.Table_aliasContext ctx) {
        return null;
    }

    @Override
    public Schema visitWith_table_hints(TSqlParser.With_table_hintsContext ctx) {
        return null;
    }

    @Override
    public Schema visitInsert_with_table_hints(TSqlParser.Insert_with_table_hintsContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_hint(TSqlParser.Table_hintContext ctx) {
        return null;
    }

    @Override
    public Schema visitIndex_value(TSqlParser.Index_valueContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_alias_list(TSqlParser.Column_alias_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_alias(TSqlParser.Column_aliasContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_value_constructor(TSqlParser.Table_value_constructorContext ctx) {
        return null;
    }

    @Override
    public Schema visitExpression_list(TSqlParser.Expression_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitRanking_windowed_function(TSqlParser.Ranking_windowed_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAggregate_windowed_function(TSqlParser.Aggregate_windowed_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAnalytic_windowed_function(TSqlParser.Analytic_windowed_functionContext ctx) {
        return null;
    }

    @Override
    public Schema visitAll_distinct_expression(TSqlParser.All_distinct_expressionContext ctx) {
        return null;
    }

    @Override
    public Schema visitOver_clause(TSqlParser.Over_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitRow_or_range_clause(TSqlParser.Row_or_range_clauseContext ctx) {
        return null;
    }

    @Override
    public Schema visitWindow_frame_extent(TSqlParser.Window_frame_extentContext ctx) {
        return null;
    }

    @Override
    public Schema visitWindow_frame_bound(TSqlParser.Window_frame_boundContext ctx) {
        return null;
    }

    @Override
    public Schema visitWindow_frame_preceding(TSqlParser.Window_frame_precedingContext ctx) {
        return null;
    }

    @Override
    public Schema visitWindow_frame_following(TSqlParser.Window_frame_followingContext ctx) {
        return null;
    }

    @Override
    public Schema visitCreate_database_option(TSqlParser.Create_database_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDatabase_filestream_option(TSqlParser.Database_filestream_optionContext ctx) {
        return null;
    }

    @Override
    public Schema visitDatabase_file_spec(TSqlParser.Database_file_specContext ctx) {
        return null;
    }

    @Override
    public Schema visitFile_group(TSqlParser.File_groupContext ctx) {
        return null;
    }

    @Override
    public Schema visitFile_spec(TSqlParser.File_specContext ctx) {
        return null;
    }

    @Override
    public Schema visitEntity_name(TSqlParser.Entity_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitEntity_name_for_azure_dw(TSqlParser.Entity_name_for_azure_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitEntity_name_for_parallel_dw(TSqlParser.Entity_name_for_parallel_dwContext ctx) {
        return null;
    }

    @Override
    public Schema visitFull_table_name(TSqlParser.Full_table_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitTable_name(TSqlParser.Table_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitSimple_name(TSqlParser.Simple_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitFunc_proc_name(TSqlParser.Func_proc_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitDdl_object(TSqlParser.Ddl_objectContext ctx) {
        return null;
    }

    @Override
    public Schema visitFull_column_name(TSqlParser.Full_column_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_name_list_with_order(TSqlParser.Column_name_list_with_orderContext ctx) {
        return null;
    }

    @Override
    public Schema visitColumn_name_list(TSqlParser.Column_name_listContext ctx) {
        return null;
    }

    @Override
    public Schema visitCursor_name(TSqlParser.Cursor_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitOn_off(TSqlParser.On_offContext ctx) {
        return null;
    }

    @Override
    public Schema visitClustered(TSqlParser.ClusteredContext ctx) {
        return null;
    }

    @Override
    public Schema visitNull_notnull(TSqlParser.Null_notnullContext ctx) {
        return null;
    }

    @Override
    public Schema visitNull_or_default(TSqlParser.Null_or_defaultContext ctx) {
        return null;
    }

    @Override
    public Schema visitScalar_function_name(TSqlParser.Scalar_function_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitBegin_conversation_timer(TSqlParser.Begin_conversation_timerContext ctx) {
        return null;
    }

    @Override
    public Schema visitBegin_conversation_dialog(TSqlParser.Begin_conversation_dialogContext ctx) {
        return null;
    }

    @Override
    public Schema visitContract_name(TSqlParser.Contract_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitService_name(TSqlParser.Service_nameContext ctx) {
        return null;
    }

    @Override
    public Schema visitEnd_conversation(TSqlParser.End_conversationContext ctx) {
        return null;
    }

    @Override
    public Schema visitWaitfor_conversation(TSqlParser.Waitfor_conversationContext ctx) {
        return null;
    }

    @Override
    public Schema visitGet_conversation(TSqlParser.Get_conversationContext ctx) {
        return null;
    }

    @Override
    public Schema visitQueue_id(TSqlParser.Queue_idContext ctx) {
        return null;
    }

    @Override
    public Schema visitSend_conversation(TSqlParser.Send_conversationContext ctx) {
        return null;
    }

    @Override
    public Schema visitData_type(TSqlParser.Data_typeContext ctx) {
        return null;
    }

    @Override
    public Schema visitDefault_value(TSqlParser.Default_valueContext ctx) {
        return null;
    }

    @Override
    public Schema visitConstant(TSqlParser.ConstantContext ctx) {
        return null;
    }

    @Override
    public Schema visitSign(TSqlParser.SignContext ctx) {
        return null;
    }

    @Override
    public Schema visitId(TSqlParser.IdContext ctx) {
        return null;
    }

    @Override
    public Schema visitSimple_id(TSqlParser.Simple_idContext ctx) {
        return null;
    }

    @Override
    public Schema visitComparison_operator(TSqlParser.Comparison_operatorContext ctx) {
        return null;
    }

    @Override
    public Schema visitAssignment_operator(TSqlParser.Assignment_operatorContext ctx) {
        return null;
    }

    @Override
    public Schema visitFile_size(TSqlParser.File_sizeContext ctx) {
        return null;
    }

    @Override
    public Schema visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public Schema visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Schema visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Schema visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
