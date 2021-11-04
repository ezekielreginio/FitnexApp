package com.pupccis.fitnex.adapters;

import static com.pupccis.fitnex.handlers.view.CardHandler.hideIndicator;
import static com.pupccis.fitnex.handlers.view.CardHandler.swipeableCardOnClickHide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.activities.main.trainee.TraineeDashboard;
import com.pupccis.fitnex.activities.main.trainee.fragment.TraineeProgramsFragment;
import com.pupccis.fitnex.api.enums.AccessType;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.databinding.ItemContainerProgramBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

public class ProgramAdapter extends FirestoreRecyclerAdapter<Program, ProgramAdapter.ProgramHolder> {

    private ProgramViewModel programViewModel;
    private AccessType accessType;
    private UserPreferences userPreferences = new UserPreferences(new TraineeDashboard().getTraineeDashboardContext());

    public ProgramAdapter(@NonNull FirestoreRecyclerOptions<Program> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProgramHolder holder, int position, @NonNull Program model) {
        model.setProgramID(this.getSnapshots().getSnapshot(position).getId());
        holder.setProgramData(model);
        boolean isExpanded = model.isExpanded();
    }

    @NonNull
    @Override
    public ProgramHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerProgramBinding binding = ItemContainerProgramBinding.inflate(inflater, parent, false);
        binding.setViewModel(programViewModel);

        return new ProgramHolder(binding);
    }

    public ProgramViewModel getViewModel() {
        return programViewModel;
    }

    public void setProgramViewModel(ProgramViewModel programViewModel) {
        this.programViewModel = programViewModel;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    class ProgramHolder extends RecyclerView.ViewHolder {
        private ItemContainerProgramBinding binding;

        public ProgramHolder(@NonNull ItemContainerProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void setProgramData(Program model) {
            if(userPreferences.getString(UserConstants.KEY_USER_TYPE) == "trainee"){
                binding.swipeRevealLayoutProgramCard.setLockDrag(true);
            }
            binding.textProgramName.setText(model.getName());
            binding.textProgramDescription.setText(model.getDescription());
            binding.textProgramSessionCount.setText(model.getSessionNumber());
            binding.textProgramDuration.setText(model.getDuration() + " minutes");
            binding.textProgramCategory.setText(GlobalConstants.KEY_CATEGORY_ARRAY[model.getCategory() - 1]);
            switch (accessType) {
                case VIEW:
                    // binding.layoutProgramButtonOwner.setVisibility(View.GONE);
                    binding.layoutProgramContainer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.getViewModel().triggerContainerClicked(model);
//                            if(binding.layoutProgramInfo.getVisibility() == View.VISIBLE)
//                                binding.programExpand.setImageResource(R.drawable.ic_expand_less);
//                            else
//                                binding.programExpand.setImageResource(R.drawable.ic_expand_more);
                        }
                    });
                    break;
            }

            switch (Privilege.valueOf(model.getPrivilege())) {
                case BRONZE:
                    binding.imageViewBadgeIcon.setImageResource(R.drawable.ic_badge_bronze);
                    break;
                case SILVER:
                    binding.imageViewBadgeIcon.setImageResource(R.drawable.ic_badge_silver);
                    break;
                case GOLD:
                    binding.imageViewBadgeIcon.setImageResource(R.drawable.ic_badge_gold);
                    break;
            }

            binding.imageView10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(binding.swipeRevealLayoutProgramCard.isOpened())
                        binding.getViewModel().triggerUpdateObserver(model);
                }
            });

            if (!model.getPrivilege().equals(Privilege.FREE.toString()))
                binding.imageViewBadgeIcon.setVisibility(View.VISIBLE);

            if(accessType == AccessType.VIEW){
                binding.swipeRevealLayoutProgramCard.setLockDrag(true);
                binding.imageViewProgramPullIndicator.setVisibility(View.GONE);
            }

            binding.imageView11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(binding.swipeRevealLayoutProgramCard.isOpened())
                        binding.getViewModel().triggerDeleteObserver(model);
                }
            });

            swipeableCardOnClickHide(binding.programNameContainer, binding.layoutProgramInfo, binding.swipeRevealLayoutProgramCard, binding.imageViewProgramExpand);
            hideIndicator(binding.swipeRevealLayoutProgramCard, binding.imageViewProgramPullIndicator);
            binding.setVariable(BR.program, model);
        }
    }
}
