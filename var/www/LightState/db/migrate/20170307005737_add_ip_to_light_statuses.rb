class AddIpToLightStatuses < ActiveRecord::Migration
  def change
    add_column :light_statuses, :ip, :string
  end
end
